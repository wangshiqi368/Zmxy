// src/components/AppLayout/index.tsx
import { Layout, Button, message, AutoComplete, Avatar, Menu } from 'antd';
import { Outlet, useNavigate, useLocation } from 'react-router-dom';
import styles from './index.module.css';
import { useState } from 'react';
import { searchContacts } from '../../services/contact';
import type { Contact } from '../../types';
import { UserOutlined } from '@ant-design/icons';
import useConstant from 'use-constant';

const { Header, Content } = Layout;

const debounce = (func: Function, delay: number) => {
    let timeout: NodeJS.Timeout;
    return (...args: any[]) => {
        clearTimeout(timeout);
        timeout = setTimeout(() => func(...args), delay);
    };
};

const navItems = [
    { key: '/', label: '仪表盘' },
    { key: '/contacts', label: '联系人' },
];

export default function AppLayout() {
    const navigate = useNavigate();
    const location = useLocation();
    const username = localStorage.getItem('username');
    const [options, setOptions] = useState<{ value: string; label: JSX.Element; key: number }[]>([]);

    const handleLogout = () => {
        localStorage.removeItem('jwt_token');
        localStorage.removeItem('username');
        message.success('您已成功退出登录');
        navigate('/login');
    };

    const handleSearch = async (value: string) => {
        if (!value) {
            setOptions([]);
            return;
        }
        try {
            const response = await searchContacts(value);
            const contacts: Contact[] = response.data;
            const searchOptions = contacts.map(contact => ({
                value: contact.name,
                key: contact.id,
                label: (
                    <div style={{ display: 'flex', alignItems: 'center' }}>
                        <Avatar icon={<UserOutlined />} src={contact.avatarUrl} size="small" />
                        <span style={{ marginLeft: 8 }}>{contact.name}</span>
                        <span style={{ marginLeft: 'auto', color: '#999', fontSize: '12px' }}>{contact.company}</span>
                    </div>
                ),
            }));
            setOptions(searchOptions);
        } catch (error) {
            console.error('Search failed:', error);
        }
    };
    
    const debouncedSearch = useConstant(() => debounce(handleSearch, 300));

    const onSelect = (_value: string, option: any) => {
        navigate(`/contacts/${option.key}`);
    };
    
    const onNavClick = ({ key }: { key: string }) => {
        navigate(key);
    }

    return (
        <Layout className={styles.layout}>
            <Header className={styles.header}>
                <div className={styles.logo}>智脉星云</div>
                
                <Menu
                    theme="light"
                    mode="horizontal"
                    selectedKeys={[location.pathname]}
                    items={navItems}
                    onClick={onNavClick}
                    className={styles.navMenu}
                />

                <div className={styles.search}>
                    <AutoComplete
                        popupMatchSelectWidth={252}
                        style={{ width: 300 }}
                        options={options}
                        onSelect={onSelect}
                        onSearch={debouncedSearch}
                        placeholder="搜索联系人 (姓名/公司/拼音)"
                    />
                </div>

                <div className={styles.userInfo}>
                    <span className={styles.username}>欢迎您, {username}</span>
                    <Button type="primary" onClick={handleLogout}>
                        退出登录
                    </Button>
                </div>
            </Header>
            <Content className={styles.content}>
                <div className={styles.contentInner}>
                    <Outlet />
                </div>
            </Content>
        </Layout>
    );
}
