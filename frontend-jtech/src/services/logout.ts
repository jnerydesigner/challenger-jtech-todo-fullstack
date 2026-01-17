import { api } from "./api";

export const logout = async () => {
    await api.post('/auth/logout')

    localStorage.removeItem('token')
    localStorage.removeItem('user_name')
    localStorage.removeItem('user_email')
}
