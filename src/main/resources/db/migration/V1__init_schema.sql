CREATE SCHEMA IF NOT EXISTS project;
SET search_path = project;

-- Sequences
CREATE SEQUENCE IF NOT EXISTS project.roles_seq START WITH 3 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS project.users_seq START WITH 1 INCREMENT BY 1;

-- Tables
CREATE TABLE users (
                       id BIGINT PRIMARY KEY,
                       username TEXT UNIQUE NOT NULL,
                       email TEXT UNIQUE NOT NULL,
                       password TEXT NOT NULL
);

CREATE TABLE roles (
                       id INTEGER PRIMARY KEY,
                       name TEXT NOT NULL
);

CREATE TABLE user_roles (
                            user_id BIGINT NOT NULL,
                            role_id INTEGER NOT NULL,
                            PRIMARY KEY (user_id, role_id),
                            FOREIGN KEY (user_id) REFERENCES project.users(id),
                            FOREIGN KEY (role_id) REFERENCES project.roles(id)
);

-- Roles insert
INSERT INTO roles (id, name) VALUES
                                 (1, 'ADMIN'),
                                 (2, 'CLIENT');
