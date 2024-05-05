CREATE TABLE IF NOT EXISTS attendances (
    id BIGSERIAL PRIMARY KEY,
    date DATE,
    time_in TIMESTAMP,
    time_out TIMESTAMP,
    status VARCHAR(10),
    remark VARCHAR(255),
    type VARCHAR(255),
    location VARCHAR(255),
    employee_id BIGINT,
    created_by BIGINT,
    created_date TIMESTAMP WITHOUT TIME ZONE,
    last_modified_by BIGINT,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE
);