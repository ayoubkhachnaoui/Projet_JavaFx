-- Schema for Gestion Étudiants & Notes
-- Technology: SQLite

-- 1. Users Table (Authentication)
CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    role TEXT NOT NULL -- 'ADMIN' or 'ENSEIGNANT'
);

-- 2. Students Table
CREATE TABLE IF NOT EXISTS students (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nom TEXT NOT NULL,
    prenom TEXT NOT NULL,
    cin TEXT UNIQUE NOT NULL,
    email TEXT NOT NULL,
    telephone TEXT NOT NULL,
    date_naissance TEXT NOT NULL,
    niveau TEXT NOT NULL, -- 'ING 1', 'ING 2', etc.
    filiere TEXT NOT NULL,
    groupe TEXT NOT NULL
);

-- 3. Modules Table
CREATE TABLE IF NOT EXISTS modules (
    code TEXT PRIMARY KEY,
    nom TEXT NOT NULL,
    coefficient REAL NOT NULL,
    enseignant TEXT NOT NULL
);

-- 4. Grades Table (Relationship between Student and Module)
CREATE TABLE IF NOT EXISTS grades (
    student_id INTEGER,
    module_code TEXT,
    note_cc REAL NOT NULL,
    note_examen REAL NOT NULL,
    PRIMARY KEY (student_id, module_code),
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    FOREIGN KEY (module_code) REFERENCES modules(code) ON DELETE CASCADE
);

-- Sample Data Seeding
INSERT OR IGNORE INTO users (username, password, role) VALUES ('ayoub', 'khachnaouiayoub', 'ADMIN');
INSERT OR IGNORE INTO users (username, password, role) VALUES ('teacher', 'teacher123', 'ENSEIGNANT');

-- Example Student
INSERT OR IGNORE INTO students (nom, prenom, cin, email, telephone, date_naissance, niveau, filiere, groupe) 
VALUES ('Dupont', 'Jean', 'A1234567', 'jean.dupont@email.com', '0612345678', '2002-05-20', 'ING 1', 'Informatique', 'G1');

-- Example Module
INSERT OR IGNORE INTO modules (code, nom, coefficient, enseignant) 
VALUES ('JAVA101', 'Programmation Java FX', 3.0, 'Prof. Martin');
