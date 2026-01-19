# Documentação do Sistema Todo Fullstack

Este documento descreve o fluxo do sistema, a arquitetura backend/frontend, e como os componentes se integram.

## 1. Fluxo do Sistema e Integração Backend-Frontend

O sistema segue uma arquitetura RESTful clássica com separação clara entre frontend (Vue.js) e backend (Spring Boot).

### Fluxo Geral

1.  **Usuário interage com a UI** (Ex: Criar tarefa, Login).
2.  **Vue.js** intercepta a ação e chama um **Service** (`services/`).
3.  **Service** faz uma requisição HTTP (Axios) para o **Backend**.
4.  **Backend (Controller)** recebe a requisição, valida DTOs e chama o **Service Layer**.
5.  **Service Layer** processa a regra de negócio e interage com o **Repository**.
6.  **Repository** (JPA/Hibernate) persiste/recupera dados do **PostgreSQL**.
7.  Resposta retorna em cascata até o frontend, que atualiza a **Store (Pinia)** e reage na UI.

### Classes Principais (Backend)

- **Controllers (`presentation/`)**: `TaskController`, `AuthController`. Pontos de entrada da API.
- **Services (`app/services/`)**: `TaskService`, `authService`. Lógica de negócio.
- **Repositories (`domain/repositories/` & `infra/database/`)**: Interfaces e implementações JPA.

## 2. Tipos DTO e Chamadas HTTP

O backend utiliza DTOs (Data Transfer Objects) para desacoplar a API das entidades de domínio.

### Exemplo: Criação de Tarefa (`POST /tasks`)

1.  **Frontend**: Envia JSON `{"title": "Nova Tarefa"}`.
2.  **Backend (`TaskController`)**:
    ```java
    @PostMapping
    public ResponseEntity<BuildUserDTO> saveTask(
            @RequestBody CreateTaskDTO createTaskDTO, // Recebe { title }
            Authentication authentication) { ... }
    ```
3.  **Conversão**: O JSON é mapeado automaticamente para `CreateTaskDTO`.
4.  **Resposta**: O backend retorna `BuildUserDTO`, que contém a lista atualizada de tarefas do usuário.

### Exemplo: Login (`POST /auth/login`)

1.  **Input (`LoginDTO`)**: `{ "email": "...", "password": "..." }`
2.  **Output (`AuthResponseDTO`)**: `{ "id": "...", "name": "...", "token": "..." }`
    - O token JWT é enviado também como Cookie HttpOnly para segurança.

## 3. Gerenciamento de Estado com Pinia

O estado da aplicação é centralizado usando **Pinia**. No arquivo `frontend/stores/useTaskStore.ts`:

- **State**: `tasks` (Armazena o objeto `UserWithTasks`).
- **Getters**: `tasksComputed` (Retorna a lista de tarefas facilitar o uso nos componentes).
- **Actions**: Encapsulam a lógica de chamar a API e atualizar o estado local.
  - Exemplo: `createTask(title)` chama `api.post`, recebe a lista atualizada e define `tasks.value = response`.
  - Isso garante "Single Source of Truth". O frontend não tenta adivinhar o estado, ele confia na resposta do backend (que retorna a lista atualizada `BuildUserDTO` em operações de escrita).

```typescript
// Exemplo simplificado do Store
export const useTaskStore = defineStore("tasks", () => {
  const tasks = ref<UserWithTasks | null>(null);

  async function createTask(title: string) {
    const response = await createNewTask(title); // Chama API
    tasks.value = response; // Atualiza Estado Reativo
  }
  return { tasks, createTask };
});
```

## 4. Rotas do Frontend (Vue Router)

As rotas são definidas em `router/index.ts` e protegidas com **Navigation Guards**.

- **Públicas**:
  - `/login`: Tela de Login (`meta: { requiresAuth: false }`)
  - `/create-user`: Tela de Cadastro (`meta: { requiresAuth: false }`)
- **Privadas**:
  - `/`: Layout Default (`meta: { requiresAuth: true }`)
    - `/`: Dashboard (`HomeView`)

**Guarda de Navegação (`beforeEach`)**:
Verifica se a rota precisa de autenticação (`requiresAuth`). Se sim, checa se o usuário está logado na `userStore`. Se não estiver, redireciona para `/login`.

## 5. Services do Frontend

Os services (`frontend/src/services/`) são wrappers para o Axios, tipando as requisições e respostas.

- `api.ts`: Configura a instância do Axios (Base URL, Interceptors).
- `tasks.ts`: Métodos de tarefas.
  ```typescript
  export const createNewTask = async (
    title: string,
  ): Promise<UserWithTasks> => {
    const { data } = await api.post("/tasks", { title });
    return data;
  };
  ```
- `user.ts`: Métodos de usuário (Info, Create).

## 6. Inicialização do Banco de Dados

O banco de dados PostgreSQL é gerenciado via **Docker Assemble**.

Arquivo: `backend-jtech/docker-compose.yaml` (ou na raiz).

```yaml
services:
  postgres:
    image: postgres:16
    ports:
      - "5442:5432" # Porta externa 5442 mapeada para interna 5432
    environment:
      POSTGRES_DB: jtech-todo-db
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    volumes:
      - postgres_data:/var/lib/postgresql/data # Persistência
```

**Como iniciar:**
Execute o comando no terminal na raiz do projeto (onde está o `docker-compose.yaml`):

```bash
docker-compose up -d
```
