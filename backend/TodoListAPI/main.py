from fastapi import FastAPI, HTTPException, Depends
from fastapi.security import OAuth2PasswordRequestForm
from database import get_db
import sqlite3

app = FastAPI()

# Função para criar as tabelas no banco de dados, caso não existam
def create_tables():
    with get_db() as db:
        db.execute("""
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT UNIQUE,
                password TEXT
            )
        """)
        db.execute("""
            CREATE TABLE IF NOT EXISTS todos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                user_id INTEGER,
                FOREIGN KEY (user_id) REFERENCES users(id)
            )
        """)

create_tables()

@app.post("/login")
async def login(form_data: OAuth2PasswordRequestForm = Depends()):
    try:
        with get_db() as db:
            user = db.execute("SELECT * FROM users WHERE username = ?", (form_data.username,)).fetchone()
            if not user or user["password"] != form_data.password:
                raise HTTPException(status_code=401, detail="Usuário ou senha inválidos")
        return {"message": "Login bem-sucedido", "user_id": user["id"]}
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Erro de conexão com o banco de dados: {str(e)}")


# Criar uma nova tarefa (todo)
@app.post("/todos/")
async def create_todo(name: str, user_id: int):
    with get_db() as db:
        # Verifica se o usuário existe
        user = db.execute("SELECT * FROM users WHERE id = ?", (user_id,)).fetchone()
        if not user:
            raise HTTPException(status_code=404, detail="Usuário não encontrado")

        db.execute("INSERT INTO todos (name, user_id) VALUES (?, ?)", (name, user_id))
        return {"message": "Tarefa criada com sucesso"}

# Buscar todas as tarefas de um usuário
@app.get("/todos/{user_id}")
async def get_todos(user_id: int):
    with get_db() as db:
        todos = db.execute("SELECT * FROM todos WHERE user_id = ?", (user_id,)).fetchall()
        if not todos:
            raise HTTPException(status_code=404, detail="Nenhuma tarefa encontrada")
        
        return [{"id": todo["id"], "name": todo["name"], "user_id": todo["user_id"]} for todo in todos]
