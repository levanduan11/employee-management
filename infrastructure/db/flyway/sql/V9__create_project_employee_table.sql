CREATE TABLE IF NOT EXISTS project_employee (
    project_id BIGINT NOT NULL,
    employee_id BIGINT NOT NULL,
    PRIMARY KEY (project_id, employee_id)
);