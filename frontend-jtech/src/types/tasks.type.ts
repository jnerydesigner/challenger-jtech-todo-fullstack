export interface UserWithTasks {
    id: string
    name: string
    email: string
    tasks: Task[]
}

export interface Task {
    id: string
    title: string
    description: string
    completed: boolean
    createdAt: string
    updatedAt: string
}
