// src/pages/Dashboard/index.tsx
import { useEffect, useState } from 'react';
import { getUpcomingFollowUps, toggleFollowUpStatus } from '../../services/interaction';
import { List, message, Spin, Typography, Checkbox, Tag } from 'antd';
import { Link } from 'react-router-dom';
import type { Interaction } from '../../types';
import styles from './index.module.css';

const { Title, Text } = Typography;

export default function DashboardPage() {
    const [followUps, setFollowUps] = useState<Interaction[]>([]);
    const [loading, setLoading] = useState(true);

    const fetchFollowUps = async () => {
        try {
            setLoading(true);
            const response = await getUpcomingFollowUps();
            setFollowUps(response.data);
        } catch (error) {
            message.error('获取待办列表失败');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchFollowUps();
    }, []);

    const handleToggle = async (id: number) => {
        try {
            await toggleFollowUpStatus(id);
            fetchFollowUps();
        } catch (error) {
            message.error('更新状态失败');
        }
    };

    if (loading) return <Spin size="large" className={styles.spinner} />;

    return (
        <div>
            <Title level={2}>近期待办事项</Title>
            <List
                dataSource={followUps}
                renderItem={(item) => (
                    <List.Item actions={[<Checkbox checked={!!item.isFollowUpCompleted} onChange={() => handleToggle(item.id)} />]}>
                        <List.Item.Meta
                            title={<Link to={`/contacts/${item.contactId}`}>与 {item.contactName} 的跟进</Link>}
                            description={<><Tag color="red">截止: {item.nextFollowUpDate}</Tag><Text>{item.summary}</Text></>}
                        />
                    </List.Item>
                )}
            />
        </div>
    );
}
