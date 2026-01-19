import { api } from "./api";

export const userInfo = async () => {
  const response = await api.get('/user/info-me');
  return response.data;
}

export const createUser = async (name: string, email: string, password: string) => {
  const { data } = await api.post('/users', { name, email, password });
  return data;
}
