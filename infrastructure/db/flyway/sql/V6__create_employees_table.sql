create TABLE IF NOT EXISTS employees (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    phone VARCHAR(50),
    image_url TEXT,
    gender VARCHAR(10),
    birth_date DATE,
    salary DECIMAL(15, 2),
    hire_date DATE,
    created_by BIGINT,
    created_date TIMESTAMP WITHOUT TIME ZONE,
    last_modified_by BIGINT,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE
);