export interface UserWithTasks {
  id: string
  name: string
  email: string
  tasks: TaskType[]
}

export interface TaskType {
  id: string
  title: string
  completed: boolean
  createdAt: string
  updatedAt: string
}
