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