// src/pages/ContactList/index.tsx
import { useEffect, useState } from 'react';
import { createContact, getContactList, updateContact, deleteContact } from '../../services/contact';
import { Avatar, Button, Collapse, List, message, Popconfirm, Spin, Typography } from 'antd';
import { EditOutlined, DeleteOutlined, PlusOutlined, UserOutlined } from '@ant-design/icons';
import styles from './index.module.css';
import type { Contact, ContactFormData } from '../../types';
import ContactFormModal from '../../components/ContactFormModal';

const { Panel } = Collapse;
const { Title } = Typography;

type GroupedContacts = Record<string, Contact[]>;

export default function ContactListPage() {
    const [contacts, setContacts] = useState<GroupedContacts>({});
    const [loading, setLoading] = useState(true);
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [editingContact, setEditingContact] = useState<Contact | null>(null);

    const fetchContacts = async () => {
        try {
            setLoading(true);
            const response = await getContactList();
            setContacts(response.data);
        } catch (error) {
            message.error('获取联系人列表失败');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => { fetchContacts(); }, []);

    const handleOpenModal = (contact?: Contact) => {
        setEditingContact(contact || null);
        setIsModalVisible(true);
    };

    const handleFinish = async (values: ContactFormData) => {
        try {
            if (editingContact) {
                await updateContact(editingContact.id, values);
                message.success('更新成功');
            } else {
                await createContact(values);
                message.success('创建成功');
            }
            setIsModalVisible(false);
            fetchContacts();
        } catch (error) { message.error('操作失败'); }
    };

    const handleDelete = async (id: number) => {
        try {
            await deleteContact(id);
            message.success('删除成功');
            fetchContacts();
        } catch (error) { message.error('删除失败'); }
    };

    if (loading && !isModalVisible) return <Spin size="large" className={styles.spinner} />;

    const collapseItems = Object.entries(contacts).map(([group, list]) => ({
        key: group,
        label: `${group} (${list.length})`,
        children: (
            <List
                dataSource={list}
                renderItem={(item) => (
                    <List.Item actions={[
                        <Button type="link" icon={<EditOutlined />} onClick={() => handleOpenModal(item)}>编辑</Button>,
                        <Popconfirm title="确定删除？" onConfirm={() => handleDelete(item.id)}><Button type="link" icon={<DeleteOutlined />} danger>删除</Button></Popconfirm>
                    ]}>
                        <List.Item.Meta
                            avatar={item.avatarUrl ? <Avatar src={item.avatarUrl} /> : <Avatar icon={<UserOutlined />} />}
                            title={<a href={`/contacts/${item.id}`}>{item.name}</a>}
                            description={`${item.company || ''} - ${item.title || ''}`}
                        />
                    </List.Item>
                )}
            />
        )
    }));

    return (
        <div>
            <div className={styles.pageHeader}>
                <Title level={2}>我的联系人</Title>
                <Button type="primary" icon={<PlusOutlined />} onClick={() => handleOpenModal()}>新建联系人</Button>
            </div>
            <Collapse ghost items={collapseItems} />
            <ContactFormModal visible={isModalVisible} onCancel={() => setIsModalVisible(false)} onFinish={handleFinish} initialValues={editingContact} />
        </div>
    );
}
