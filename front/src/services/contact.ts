// src/services/contact.ts
import type { ContactFormData } from '../types';
import apiClient from './api';

export const getContactList = () => {
    return apiClient.get('/contacts');
};

export const getContactDetail = (id: number) => {
    return apiClient.get(`/contacts/${id}`);
};

export const searchContacts = (keyword: string) => {
    return apiClient.get('/contacts/search', { params: { keyword } });
};

export const createContact = (data: ContactFormData) => {
    return apiClient.post('/contacts', data);
};

export const updateContact = (id: number, data: ContactFormData) => {
    return apiClient.put(`/contacts/${id}`, data);
};

export const deleteContact = (id: number) => {
    return apiClient.delete(`/contacts/${id}`);
}

export const getRecentContacts = () => {
    return apiClient.get('/contacts/recent');
};
