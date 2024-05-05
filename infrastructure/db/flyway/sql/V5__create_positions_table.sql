CREATE TABLE IF NOT EXISTS positions (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    created_by BIGINT,
    created_date TIMESTAMP WITHOUT TIME ZONE,
    last_modified_by BIGINT,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE
);