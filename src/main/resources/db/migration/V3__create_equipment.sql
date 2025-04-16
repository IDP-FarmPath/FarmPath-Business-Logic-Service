CREATE SEQUENCE IF NOT EXISTS project.equipment_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE project.equipment (
                                   id BIGINT PRIMARY KEY,
                                   name TEXT NOT NULL,
                                   description TEXT,
                                   price_per_day DOUBLE PRECISION,
                                   category_id BIGINT,
                                   CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES project.category(id)
);
