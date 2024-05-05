create table if not exists "roles" (
    id bigserial primary key,
    name varchar(255) not null unique created_by bigint,
    created_date timestamp without time zone,
    last_modified_by bigint,
    last_modified_date timestamp without time zone
);

create table if not exists "users" (
    id bigserial primary key,
    username varchar(255) not null unique,
    email varchar(255) not null unique,
    password varchar(255) not null,
    activated boolean not null default false,
    activation_key varchar(255),
    reset_key varchar(255),
    reset_date timestamp without time zone,
    created_by bigint,
    created_date timestamp without time zone,
    last_modified_by bigint,
    last_modified_date timestamp without time zone
);

create table if not exists "user_roles" (
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id)
);

create table if not exists "projects" (
    id bigserial primary key,
    name varchar(500) not null unique,
    description text,
    start_date timestamp without time zone,
    end_date timestamp without time zone,
    created_by bigint,
    created_date timestamp without time zone,
    last_modified_by bigint,
    last_modified_date timestamp without time zone
);

create table if not exists "positions" (
    id bigserial primary key,
    title varchar(500) not null unique,
    description text,
    created_by bigint,
    created_date timestamp without time zone,
    last_modified_by bigint,
    last_modified_date timestamp without time zone
);

create table if not exists "employees" (
    id bigserial primary key,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    phone varchar(50) not null,
    image_url text,
    gender varchar(10) not null,
    birth_date date not null,
    salary numeric(14, 2) not null,
    hire_date date not null,
    created_by bigint,
    created_date timestamp without time zone,
    last_modified_by bigint,
    last_modified_date timestamp without time zone
);

create table if not exists "departments" (
    id bigserial primary key,
    name varchar(500) not null unique,
    location varchar(500),
    created_by bigint,
    created_date timestamp without time zone,
    last_modified_by bigint,
    last_modified_date timestamp without time zone
);

create table if not exists "department_project" (
    department_id bigint not null,
    project_id bigint not null,
    primary key (department_id, project_id)
);

create table if not exists "attendances" (
    id bigserial primary key,
    date date not null,
    time_in timestamp without time zone,
    time_out timestamp without time zone,
    status varchar(50) not null,
    remarks text,
    location varchar(500),
    employee_id bigint not null,
    created_by bigint,
    created_date timestamp without time zone,
    last_modified_by bigint,
    last_modified_date timestamp without time zone
);