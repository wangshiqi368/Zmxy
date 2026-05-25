// src/pages/ContactDetail/index.tsx
import { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { getContactDetail } from '../../services/contact';
import { addInteraction } from '../../services/interaction';
import { Descriptions, message, Spin, Tag, Timeline, Typography, Button, Space } from 'antd';
import { ArrowLeftOutlined, EditOutlined, PlusOutlined } from '@ant-design/icons';
import type { ContactDetail, InteractionFormData } from '../../types';
import styles from './index.module.css';
import { format } from 'date-fns';
import InteractionFormModal from '../../components/InteractionFormModal';

const { Title, Text } = Typography;

export default function ContactDetailPage() {
    const { id } = useParams<{ id: string }>();
    const [contactDetail, setContactDetail] = useState<ContactDetail | null>(null);
    const [loading, setLoading] = useState(true);
    const [isInteractionModalVisible, setIsInteractionModalVisible] = useState(false);

    const fetchDetail = async () => {
        if (!id) return;
        try {
            setLoading(true);
            const response = await getContactDetail(parseInt(id, 10));
            setContactDetail(response.data);
        } catch (error) { message.error('获取详情失败'); } finally { setLoading(false); }
    };

    useEffect(() => { fetchDetail(); }, [id]);

    const handleAddInteraction = async (values: any) => {
        if (!id) return;
        const interactionData: InteractionFormData = {
            ...values,
            contactId: parseInt(id, 10),
            interactionTime: values.interactionTime.format('YYYY-MM-DD HH:mm:ss'),
            nextFollowUpDate: values.nextFollowUpDate ? values.nextFollowUpDate.format('YYYY-MM-DD') : undefined,
        };
        try {
            await addInteraction(interactionData);
            message.success('添加成功');
            setIsInteractionModalVisible(false);
            fetchDetail();
        } catch(error) { message.error('添加失败'); }
    };

    if (loading) return <Spin size="large" className={styles.spinner} />;
    if (!contactDetail) return <p>未找到信息。</p>;

    const { baseInfo, tags, timeline } = contactDetail;

    return (
        <div>
            <div className={styles.pageHeader}>
                <Link to="/contacts"><Button icon={<ArrowLeftOutlined />}>返回</Button></Link>
                <Space>
                    <Button icon={<PlusOutlined />} onClick={() => setIsInteractionModalVisible(true)}>追加互动</Button>
                    <Button type="primary" icon={<EditOutlined />}>编辑</Button>
                </Space>
            </div>
            <Title level={2}>{baseInfo.name}</Title>
            <div className={styles.tagSection}>{tags.map(tag => (<Tag color={tag.colorCode || 'blue'} key={tag.id}>{tag.name}</Tag>))}</div>
            <Descriptions bordered column={2}>
                <Descriptions.Item label="公司">{baseInfo.company}</Descriptions.Item>
                <Descriptions.Item label="职位">{baseInfo.title}</Descriptions.Item>
                <Descriptions.Item label="电话">{baseInfo.phone}</Descriptions.Item>
                <Descriptions.Item label="邮箱">{baseInfo.email}</Descriptions.Item>
                <Descriptions.Item label="微信号" span={2}>{baseInfo.wechatId}</Descriptions.Item>
                <Descriptions.Item label="备注" span={2}>{baseInfo.remark}</Descriptions.Item>
            </Descriptions>
            <div className={styles.timelineSection}>
                <Title level={4}>互动时间线</Title>
                <Timeline>
                    {timeline.map(item => (
                        <Timeline.Item key={item.id}>
                            <p><strong>{format(new Date(item.interactionTime), 'yyyy-MM-dd HH:mm')}</strong> - <Text type="secondary">({item.interactionType})</Text></p>
                            <p>{item.summary}</p>
                            {item.nextFollowUpDate && <Tag color={item.isFollowUpCompleted ? 'green' : 'red'}>{item.isFollowUpCompleted ? '已完成' : `待跟进: ${item.nextFollowUpDate}`}</Tag>}
                        </Timeline.Item>
                    ))}
                </Timeline>
            </div>
            <InteractionFormModal visible={isInteractionModalVisible} onCancel={() => setIsInteractionModalVisible(false)} onFinish={handleAddInteraction} />
        </div>
    );
}
