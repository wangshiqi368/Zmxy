// src/pages/Login/index.tsx
import { Button, Card, Form, Input, message } from 'antd';
import { useNavigate } from 'react-router-dom';
import { login } from '../../services/auth';
import type { AuthRequestDTO, AuthResponseDTO } from '../../types';
import styles from './index.module.css';

export default function LoginPage() {
    const navigate = useNavigate();

    const onFinish = async (values: AuthRequestDTO) => {
        try {
            const response = await login(values);
            const data: AuthResponseDTO = response.data;
            localStorage.setItem('jwt_token', data.token);
            localStorage.setItem('username', data.username);
            message.success('登录成功！');
            navigate('/');
        } catch (error: any) {
            message.error('登录失败，请检查用户名和密码');
        }
    };

    return (
        <div className={styles.container}>
            <Card title="智脉星云 - 登录" style={{ width: 400 }}>
                <Form onFinish={onFinish} layout="vertical">
                    <Form.Item label="用户名" name="username" rules={[{ required: true }]}>
                        <Input />
                    </Form.Item>
                    <Form.Item label="密码" name="password" rules={[{ required: true }]}>
                        <Input.Password />
                    </Form.Item>
                    <Button type="primary" htmlType="submit" block>登录</Button>
                </Form>
            </Card>
        </div>
    );
}
