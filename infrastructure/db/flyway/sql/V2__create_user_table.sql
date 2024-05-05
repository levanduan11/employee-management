CREATE TABLE IF NOT EXISTS users
(
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    activated BOOLEAN NOT NULL DEFAULT false,
    activation_key VARCHAR(255),
    reset_key VARCHAR(255),
    reset_date TIMESTAMP WITHOUT TIME ZONE,
    created_by BIGINT,
    created_date TIMESTAMP WITHOUT TIME ZONE,
    last_modified_by BIGINT,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE
)