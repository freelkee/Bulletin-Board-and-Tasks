--DROP DATABASE IF EXISTS bulletin_board_and_tasks;
--CREATE DATABASE bulletin_board_and_tasks;


drop table if exists tasks;
drop table if exists announcements;
drop table if exists users;

create table public.users
(
    username  varchar(60)       not null
        constraint uniq_users primary key
        unique,
    password  varchar(60)       not null,
    completed integer default 0 not null
);

INSERT INTO users (username, password) VALUES ('Даня','qwerty');
INSERT INTO users (username, password) VALUES ('Юра','123456');
INSERT INTO users (username, password) VALUES ('Женя','password');

create table public.tasks
(
    id        bigserial
        primary key,
    task_name varchar(60) not null,
    subtasks  varchar(60),
    assignee  varchar(60),
    deadline  date,
    author    varchar(60) not null references users(username),
    is_done   boolean default false
);

INSERT INTO tasks (task_name, subtasks,assignee,deadline,author) VALUES ('Помыть посуду', 'Помыть тарелки, столовые приборы, кастрюлю сковородку','','2023-06-06','Даня');
INSERT INTO tasks (task_name, subtasks,assignee,deadline,author) VALUES ('Убраться', 'Пропылесосить, помыть пол, убрать сан узел','','2023-03-25','Юра');
INSERT INTO tasks (task_name, subtasks,assignee,deadline,author) VALUES ('Купить продукты', 'Масло, чай, сахар, туалетная бумага','','2024-02-12','Женя');

create table public.announcements
(
    id          serial primary key,
    title       varchar(100) not null,
    author      varchar(50)  not null references users(username),
    description text         not null
);

INSERT INTO announcements (title, author,description) VALUES ('Отключение горячей воды', 'Даня','С 1-го Апреля до 7-го отключат горячую воду!');
INSERT INTO announcements (title, author,description) VALUES ('Не забывайте протирать кухонный стол', 'Женя','Я уже устал убирать за вами КРОШКИ! Убирайте за собой.');
INSERT INTO announcements (title, author,description) VALUES ('Ведите себя тише ночью', 'Юра','Не шумите, я пытаюсь наладить режим');