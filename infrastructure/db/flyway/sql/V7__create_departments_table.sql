CREATE TABLE IF NOT EXISTS departments (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    location VARCHAR(255),
    created_by BIGINT,
    created_date TIMESTAMP WITHOUT TIME ZONE,
    last_modified_by BIGINT,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE
);