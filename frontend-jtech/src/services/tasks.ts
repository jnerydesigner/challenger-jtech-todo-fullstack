import type { TaskType, UserWithTasks } from "@/types/tasks.type";
import { api } from "./api";

export const tasksFindAll = async (): Promise<UserWithTasks> => {
  const { data } = await api.get<UserWithTasks>('/tasks');
  return data;
}

export const toogleTaskStatus = async (id: string): Promise<UserWithTasks> => {
  const { data } = await api.patch(`/tasks/status/${id}`);
  return data;
}

export const createNewTask = async (title: string): Promise<UserWithTasks> => {
  const { data } = await api.post('/tasks', { title });
  return data;
}

export const updateTask = async (id: string, title: string): Promise<UserWithTasks> => {
  const { data } = await api.patch(`/tasks/${id}`, { title });
  return data;
}

export const deleteTask = async (id: string): Promise<UserWithTasks> => {
  const { data } = await api.delete(`/tasks/${id}`);
  return data;
}

export const findTaskById = async (id: string): Promise<TaskType> => {
  const { data } = await api.get(`/tasks/${id}`);
  return data;
}
