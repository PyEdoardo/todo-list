import sqlite3
from contextlib import contextmanager

DATABASE = "banco.db"

@contextmanager
def get_db():
    db = sqlite3.connect(DATABASE)
    db.row_factory = sqlite3.Row  # Para acessar as colunas pelo nome
    cursor = db.cursor()
    try:
        yield cursor
        db.commit()
    finally:
        db.close()
