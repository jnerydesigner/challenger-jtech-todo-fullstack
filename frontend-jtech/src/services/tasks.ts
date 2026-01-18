import type { UserWithTasks } from "@/types/tasks.type";
import { api } from "./api";

export const tasksFindAll = async (): Promise<UserWithTasks> => {
  const response = await api.get<UserWithTasks>('/tasks');
  return response.data;
}

export const toogleTaskStatus = async (id: string) => {
  const response = await api.patch(`/tasks/status/${id}`);
  return response.data;
}
