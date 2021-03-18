
-- Создаем табличку пользователей
-- create table <table_name>
create table users (
    -- <column_name> <type> <constraints>,
    -- String = varchar

    -- primary key - первичный ключ
   id int primary key, -- primary key ~ not null + unique
   login varchar(20) not null unique,
   password varchar(100) not null,
   email varchar(100) not null unique,
   name varchar(50)
);

create table boards (
    -- serial ~ bigint auto_increment unsigned
    board_id serial primary key,
    name varchar(30) not null,
    favourite boolean default false
);

-- Добавим строки в таблицу users
-- Не указываем колонки
insert into users
values
    (1, 'admin', 'admin', 'admin@exmaple.com', 'Ivan'),
    (2, 'root', 'root', 'root@example.com', 'Dmitry');

insert into users (login, email, name, password, id)
values
    ('invanov', 'inv@yandex.ru', 'Petr', 'panov', 4);

-- Прочитаем значения/строки из таблицы users
select * from users;


insert into boards (name)
values
    ('Test board');
insert into boards (name, favourite)
values
    ('Personal education', true);

select * from boards;

select password, email, login from users;
