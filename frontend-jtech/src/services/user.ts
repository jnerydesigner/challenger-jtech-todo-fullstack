import { api } from "./api";

export const userInfo = async () => {
    const response = await api.get('/user/info-me');
    return response.data;
}