// src/components/ContactFormModal/index.tsx
import { Modal, Form, Input } from 'antd';
import { useEffect } from 'react';

interface ContactFormModalProps {
    visible: boolean;
    onCancel: () => void;
    onFinish: (values: any) => void;
    initialValues?: any;
}

export default function ContactFormModal({
    visible,
    onCancel,
    onFinish,
    initialValues,
}: ContactFormModalProps) {
    const [form] = Form.useForm();
    const isEdit = !!initialValues;

    useEffect(() => {
        if (visible) {
            form.setFieldsValue(initialValues || {});
        } else {
            form.resetFields();
        }
    }, [initialValues, visible, form]);

    return (
        <Modal
            title={isEdit ? '编辑联系人' : '新建联系人'}
            open={visible}
            onCancel={onCancel}
            onOk={() => form.submit()}
            destroyOnClose={true}
        >
            <Form form={form} layout="vertical" onFinish={onFinish} name="contactForm">
                <Form.Item
                    name="name"
                    label="姓名"
                    rules={[{ required: true, message: '请输入姓名' }]}
                >
                    <Input />
                </Form.Item>
                <Form.Item name="company" label="公司">
                    <Input />
                </Form.Item>
                <Form.Item name="title" label="职位">
                    <Input />
                </Form.Item>
                <Form.Item name="phone" label="电话">
                    <Input />
                </Form.Item>
                <Form.Item name="email" label="邮箱">
                    <Input type="email" />
                </Form.Item>
                <Form.Item name="wechatId" label="微信号">
                    <Input />
                </Form.Item>
                <Form.Item name="remark" label="备注">
                    <Input.TextArea rows={4} />
                </Form.Item>
            </Form>
        </Modal>
    );
}
