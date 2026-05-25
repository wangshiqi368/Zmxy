// src/services/interaction.ts
import type { InteractionFormData } from '../types';
import apiClient from './api';

export const addInteraction = (data: InteractionFormData) => {
    return apiClient.post('/interactions', data);
};

export const getUpcomingFollowUps = () => {
    return apiClient.get('/interactions/upcoming');
};

export const toggleFollowUpStatus = (id: number) => {
    return apiClient.patch(`/interactions/${id}/toggle-status`);
};
