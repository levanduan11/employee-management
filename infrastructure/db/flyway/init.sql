-- roles table
CREATE TABLE IF NOT EXISTS roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    created_by BIGINT,
    created_date TIMESTAMP WITHOUT TIME ZONE,
    last_modified_by BIGINT,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE
);
-- users table
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
-- user_roles table
CREATE TABLE IF NOT EXISTS user_roles
(
    user_id BIGINT,
    role_id BIGINT,
    PRIMARY KEY (user_id, role_id)
)
-- projects table
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

-- positions table
CREATE TABLE IF NOT EXISTS positions (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    created_by BIGINT,
    created_date TIMESTAMP WITHOUT TIME ZONE,
    last_modified_by BIGINT,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE
);
-- employees table
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
-- departments table
CREATE TABLE IF NOT EXISTS departments (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    location VARCHAR(255),
    created_by BIGINT,
    created_date TIMESTAMP WITHOUT TIME ZONE,
    last_modified_by BIGINT,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE
);

-- attendances table
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
-- project_employee table
CREATE TABLE IF NOT EXISTS project_employee (
    project_id BIGINT NOT NULL,
    employee_id BIGINT NOT NULL,
    PRIMARY KEY (project_id, employee_id)
);
-- department_project table
CREATE TABLE IF NOT EXISTS department_project (
    department_id BIGINT NOT NULL,
    project_id BIGINT NOT NULL,
    PRIMARY KEY (department_id, project_id)
);
-- constraints
ALTER TABLE roles
ADD CONSTRAINT fk_roles_created_by
FOREIGN KEY (created_by) REFERENCES users(id);

ALTER TABLE roles
ADD CONSTRAINT fk_roles_last_modified_by
FOREIGN KEY (last_modified_by) REFERENCES users(id);

ALTER TABLE users
ADD CONSTRAINT fk_users_created_by
FOREIGN KEY (created_by) REFERENCES users(id);

ALTER TABLE users
ADD CONSTRAINT fk_users_last_modified_by
FOREIGN KEY (last_modified_by) REFERENCES users(id);

ALTER TABLE user_roles
ADD CONSTRAINT fk_user_roles_user_id
FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE user_roles
ADD CONSTRAINT fk_user_roles_role_id
FOREIGN KEY (role_id) REFERENCES roles(id);

ALTER TABLE projects
ADD CONSTRAINT fk_projects_created_by
FOREIGN KEY (created_by) REFERENCES users(id);

ALTER TABLE projects
ADD CONSTRAINT fk_projects_last_modified_by
FOREIGN KEY (last_modified_by) REFERENCES users(id);


ALTER TABLE project_employee
ADD CONSTRAINT fk_project_employee_project_id
FOREIGN KEY (project_id) REFERENCES projects(id);

ALTER TABLE project_employee
ADD CONSTRAINT fk_project_employee_employee_id
FOREIGN KEY (employee_id) REFERENCES employees(id);

ALTER TABLE department_project
ADD CONSTRAINT fk_department_project_department_id
FOREIGN KEY (department_id) REFERENCES departments(id);

ALTER TABLE department_project
ADD CONSTRAINT fk_department_project_project_id
FOREIGN KEY (project_id) REFERENCES projects(id);

ALTER TABLE positions
ADD CONSTRAINT fk_positions_created_by
FOREIGN KEY (created_by) REFERENCES users(id);

ALTER TABLE positions
ADD CONSTRAINT fk_positions_last_modified_by
FOREIGN KEY (last_modified_by) REFERENCES users(id);

ALTER TABLE employees
ADD CONSTRAINT fk_employees_created_by
FOREIGN KEY (created_by) REFERENCES users(id);

ALTER TABLE employees
ADD CONSTRAINT fk_employees_last_modified_by
FOREIGN KEY (last_modified_by) REFERENCES users(id);

ALTER TABLE departments
ADD CONSTRAINT fk_departments_created_by
FOREIGN KEY (created_by) REFERENCES users(id);

ALTER TABLE departments
ADD CONSTRAINT fk_departments_last_modified_by
FOREIGN KEY (last_modified_by) REFERENCES users(id);

ALTER TABLE attendances
ADD CONSTRAINT fk_attendances_created_by
FOREIGN KEY (created_by) REFERENCES users(id);

ALTER TABLE attendances
ADD CONSTRAINT fk_attendances_last_modified_by
FOREIGN KEY (last_modified_by) REFERENCES users(id);

ALTER TABLE attendances
ADD CONSTRAINT fk_attendances_employee_id
FOREIGN KEY (employee_id) REFERENCES employees(id);

-- add column activation_date to table users
ALTER TABLE users
ADD COLUMN activation_date TIMESTAMP WITHOUT TIME ZONE;