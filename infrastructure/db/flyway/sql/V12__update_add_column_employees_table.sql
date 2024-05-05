ALTER TABLE employees
ADD COLUMN position_id BIGINT,
ADD CONSTRAINT fk_employees_position_id
FOREIGN KEY (position_id) REFERENCES positions(id);

ALTER TABLE employees
ADD COLUMN user_id BIGINT,
ADD CONSTRAINT fk_employees_user_id
FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE employees
ADD COLUMN department_id BIGINT,
ADD CONSTRAINT fk_employees_department_id
FOREIGN KEY (department_id) REFERENCES departments(id);

