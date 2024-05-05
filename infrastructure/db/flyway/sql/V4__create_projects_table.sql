CREATE TABLE IF NOT EXISTS projects (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    start_date DATE,
    end_date DATE,
    created_by BIGINT,
    created_date TIMESTAMP WITHOUT TIME ZONE,
    last_modified_by BIGINT,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE
);