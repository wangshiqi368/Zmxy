// src/components/InteractionFormModal/index.tsx
import { Modal, Form, Input, Select, DatePicker } from 'antd';
import { useEffect } from 'react';
import dayjs from 'dayjs';

const { Option } = Select;

interface InteractionFormModalProps {
    visible: boolean;
    onCancel: () => void;
    onFinish: (values: any) => void;
}

const interactionTypes = [
    { value: 'MEETING', label: '会议' },
    { value: 'PHONE', label: '电话' },
    { value: 'DINNER', label: '聚餐' },
    { value: 'OTHER', label: '其他' },
];

export default function InteractionFormModal({
    visible,
    onCancel,
    onFinish,
}: InteractionFormModalProps) {
    const [form] = Form.useForm();

    const handleOk = () => {
        form.submit();
    };
    
    useEffect(() => {
        if(visible) {
            form.resetFields();
            form.setFieldsValue({ interactionTime: dayjs() });
        }
    }, [visible, form]);

    return (
        <Modal
            title="追加互动记录"
            open={visible}
            onCancel={onCancel}
            onOk={handleOk}
            destroyOnClose={true}
        >
            <Form form={form} layout="vertical" onFinish={onFinish} name="interactionForm">
                <Form.Item
                    name="interactionTime"
                    label="互动时间"
                    rules={[{ required: true, message: '请选择互动时间' }]}
                >
                    <DatePicker showTime style={{ width: '100%' }} />
                </Form.Item>
                 <Form.Item
                    name="interactionType"
                    label="互动类型"
                    rules={[{ required: true, message: '请选择互动类型' }]}
                    initialValue="MEETING"
                >
                    <Select>
                        {interactionTypes.map(type => (
                            <Option key={type.value} value={type.value}>{type.label}</Option>
                        ))}
                    </Select>
                </Form.Item>
                <Form.Item
                    name="summary"
                    label="沟通纪要"
                    rules={[{ required: true, message: '请输入沟通纪要' }]}
                >
                    <Input.TextArea rows={4} />
                </Form.Item>
                <Form.Item name="nextFollowUpDate" label="下次跟进日期 (可选)">
                    <DatePicker style={{ width: '100%' }} />
                </Form.Item>
            </Form>
        </Modal>
    );
}
