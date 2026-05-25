// src/services/auth.ts
import apiClient from './api';
import type { AuthRequestDTO } from '../types';

export const login = (data: AuthRequestDTO) => {
    return apiClient.post('/auth/login', data);
};

export const register = (data: AuthRequestDTO) => {
    return apiClient.post('/auth/register', data);
};
