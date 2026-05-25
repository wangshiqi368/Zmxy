// src/router/index.tsx
import { useRoutes } from 'react-router-dom';
import LoginPage from '../pages/Login';
import ContactListPage from '../pages/ContactList';
import ProtectedRoute from './ProtectedRoute';
import AppLayout from '../components/AppLayout';
import ContactDetailPage from '../pages/ContactDetail';
import DashboardPage from '../pages/Dashboard';

export default function AppRouter() {
    const routes = useRoutes([
        { path: '/login', element: <LoginPage /> },
        {
            path: '/',
            element: <ProtectedRoute />,
            children: [
                {
                    element: <AppLayout />,
                    children: [
                        { path: '', element: <DashboardPage /> },
                        { path: 'contacts', element: <ContactListPage /> },
                        { path: 'contacts/:id', element: <ContactDetailPage /> },
                    ]
                }
            ]
        },
    ]);
    return routes;
}
