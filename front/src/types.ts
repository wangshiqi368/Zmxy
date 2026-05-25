// src/types.ts

export const API_VERSION = '1.0.0';

export interface AuthRequestDTO {
    username?: string;
    password?: string;
}

export interface AuthResponseDTO {
    token: string;
    username: string;
}

export interface ContactFormData {
    name: string;
    company?: string;
    title?: string;
    phone?: string;
    email?: string;
    wechatId?: string;
    remark?: string;
    tagIds?: number[];
}

export interface Contact {
    id: number;
    name: string;
    avatarUrl?: string;
    title?: string;
    company?: string;
    phone?: string;
    email?: string;
    wechatId?: string;
    remark?: string;
}

export interface Tag {
    id: number;
    name: string;
    colorCode: string;
}

export interface Interaction {
    id: number;
    contactId: number;
    contactName: string;
    interactionType: string;
    summary: string;
    interactionTime: string;
    nextFollowUpDate?: string;
    isFollowUpCompleted: number;
}

export interface InteractionFormData {
    contactId: number;
    interactionType: string;
    summary: string;
    interactionTime: string;
    nextFollowUpDate?: string;
}

export interface ContactDetail {
    baseInfo: Contact;
    tags: Tag[];
    timeline: Interaction[];
}
