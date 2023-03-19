DROP DATABASE IF EXISTS bulletin_board_and_tasks;
CREATE DATABASE bulletin_board_and_tasks;

drop table if exists users;
drop table if exists tasks;
drop table if exists announcements;

create table public.users
(
    id        serial
        primary key,
    username  varchar(60)       not null
        constraint uniq_users
            unique,
    password  varchar(60)       not null,
    completed integer default 0 not null
);

INSERT INTO users (username, password) VALUES ('username','password');

create table public.tasks
(
    id        bigserial
        primary key,
    task_name varchar(60) not null,
    subtasks  varchar(60),
    assignee  varchar(60),
    deadline  date,
    author    varchar(60) not null,
    is_done   boolean default false
);

INSERT INTO tasks (task_name, subtasks,assignee,deadline,author) VALUES ('Test name', 'Test subtasks','','2022-06-16','username');

create table public.announcements
(
    id          serial
        primary key,
    title       varchar(100) not null,
    author      varchar(50)  not null,
    description text         not null
);

INSERT INTO announcements (title, author,description) VALUES ('Test title', 'Test author','Test description');

