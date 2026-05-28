// src/pages/Dashboard/index.tsx
import { useEffect, useState } from 'react';
import { getUpcomingFollowUps, toggleFollowUpStatus } from '../../services/interaction';
import { getRecentContacts } from '../../services/contact';
import { message, Spin, Typography, Checkbox, Empty, Avatar, Space } from 'antd';
import { Link } from 'react-router-dom';
import type { Interaction, Contact } from '../../types';
import styles from './index.module.css';
import { isBefore, startOfDay, parseISO } from 'date-fns';
import { UserOutlined } from '@ant-design/icons';

const { Title } = Typography;

export default function DashboardPage() {
    const [followUps, setFollowUps] = useState<Interaction[]>([]);
    const [recentContacts, setRecentContacts] = useState<Contact[]>([]);
    const [loading, setLoading] = useState(true);

    const fetchData = async () => {
        try {
            setLoading(true);
            const [followUpsRes, contactsRes] = await Promise.all([
                getUpcomingFollowUps(),
                getRecentContacts()
            ]);
            
            // 待办排序
            const sortedFollowUps = followUpsRes.data.sort((a: Interaction, b: Interaction) => 
                new Date(a.nextFollowUpDate!).getTime() - new Date(b.nextFollowUpDate!).getTime()
            );
            
            setFollowUps(sortedFollowUps);
            setRecentContacts(contactsRes.data);
        } catch (error) {
            message.error('加载数据失败');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchData();
    }, []);

    const handleToggle = async (id: number) => {
        try {
            await toggleFollowUpStatus(id);
            message.success('状态已更新');
            // 只局部刷新待办列表
            const response = await getUpcomingFollowUps();
            const sorted = response.data.sort((a: Interaction, b: Interaction) => 
                new Date(a.nextFollowUpDate!).getTime() - new Date(b.nextFollowUpDate!).getTime()
            );
            setFollowUps(sorted);
        } catch (error) {
            message.error('更新失败');
        }
    };

    if (loading) return <Spin size="large" className={styles.spinner} />;

    const today = startOfDay(new Date());

    return (
        <div className={styles.dashboardContainer}>
            {/* 常用联系人区域 */}
            <div className={styles.section}>
                <Title level={4}>常用联系人</Title>
                {recentContacts.length === 0 ? (
                    <Typography.Text type="secondary">最近暂无互动联系人</Typography.Text>
                ) : (
                    <div className={styles.frequentList}>
                        {recentContacts.map(contact => (
                            <Link to={`/contacts/${contact.id}`} key={contact.id} className={styles.frequentItem}>
                                <Avatar 
                                    size={54} 
                                    src={contact.avatarUrl} 
                                    icon={<UserOutlined />} 
                                    className={styles.frequentAvatar}
                                />
                                <span className={styles.frequentName}>{contact.name}</span>
                            </Link>
                        ))}
                    </div>
                )}
            </div>

            <div className={styles.section} style={{ marginTop: 40 }}>
                <Title level={2}>近期待办</Title>
                
                {followUps.length === 0 ? (
                    <Empty description="暂无待办事项" style={{ marginTop: 20 }} />
                ) : (
                    <div className={styles.cardList}>
                        {followUps.map((item) => {
                            const dueDate = parseISO(item.nextFollowUpDate!);
                            const isOverdue = isBefore(dueDate, today);

                            return (
                                <div className={styles.todoCard} key={item.id}>
                                    <div className={styles.checkboxWrapper}>
                                        <Checkbox 
                                            checked={!!item.isFollowUpCompleted} 
                                            onChange={() => handleToggle(item.id)}
                                            style={{ transform: 'scale(1.2)' }}
                                        />
                                    </div>
                                    
                                    <div className={styles.contentWrapper}>
                                        <Link to={`/contacts/${item.contactId}`} className={styles.contactLink}>
                                            与 {item.contactName} 的跟进
                                        </Link>
                                        <p className={styles.summaryText}>{item.summary}</p>
                                    </div>
                                    
                                    <div className={styles.dateWrapper}>
                                        <span className={styles.dateLabel}>截止日期</span>
                                        <span className={isOverdue ? styles.overdue : styles.upcoming}>
                                            {item.nextFollowUpDate}
                                            {isOverdue && <span style={{ marginLeft: 4, fontSize: '12px' }}>(逾期)</span>}
                                        </span>
                                    </div>
                                </div>
                            );
                        })}
                    </div>
                )}
            </div>
        </div>
    );
}
