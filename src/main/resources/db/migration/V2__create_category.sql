CREATE SEQUENCE IF NOT EXISTS project.category_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE project.category (
                                  id BIGINT PRIMARY KEY,
                                  name TEXT NOT NULL UNIQUE
);
