-- 1-to-1 relation
create table user_credentials (
                                  user_id integer primary key, -- not null unique
                                  password varchar(100),
    -- Нужно соединить таблицу с паролями с таблицей пользователей
    -- constraint <constraint_name> foreign key (<column from current table>) references <referenced table>(<referenced column>)
    -- foreign key (user_id) references users(id) --> <table name>_<column name>_fkey - user_credentials_user_id_fkey
                                  constraint user_credentials_id_fkey foreign key (user_id) references users(id)
);

-- user_credentials
-- user_id
-- 1
-- 1 - duplicate primary key

-- 1-to-M relation
create table columns (
                         id serial primary key,
                         name varchar(50),
                         column_order integer not null,
                         board_id integer not null,
                         constraint column_board_id_fkey foreign key (board_id) references  boards(board_id)
);

-- columns
-- id | board_id
-- 1 | 1
-- 2 | 1
-- 3 | 1

-- Добавить в таблицу boards колонку user_id
-- owner_id integer not null foreign key -> users

-- 1. Добавим колонку owner_id без constaints
-- alter table <table name>
-- alter column
-- add/drop column
-- add/drop constraint <constraint name>
alter table boards
    add column owner_id integer;
-- 2. Заполним эту колонку данными
-- update <table name> set [columnName=value],[] where
update boards set owner_id = 1;
-- 3. Добавим ограничение not null
alter table boards
    alter column owner_id set not null;
-- 4. Добавим ограничение foreign key
alter table boards
    add constraint board_owner_id_fkey foreign key (owner_id) references users(id);

select * from boards;


-- Таблицы карточек
create table cards (
                       id serial primary key,
                       title varchar not null,
                       description text, -- для хранения строк, длина которых может быть больше 255 символов
                       create_date timestamp, -- дата создания + время создания
                       column_id integer not null,
                       constraint card_column_id_fkey foreign key (column_id) references columns(id)
);

-- Таблица team
create table team (
                      id serial primary key,
                      name varchar not null
);

create table team_member (
                             team_id integer not null,
                             user_id integer not null,
                             constraint team_user_ids_pkey primary key (team_id, user_id), -- composite primary key
                             constraint team_member_team_id_fkey foreign key (team_id) references team(id),
                             constraint team_member_user_id_fkey foreign key (user_id) references users(id)
);

-- Заполнение базы

insert into team (name)
values
('Hardening'),
('Dream team'),
('ZSquad'),
('NotDreamTeam'),
('SuicideSquad');

insert into users
values
(5, 'ololo', 'hello', 'ololoshechka@gmail.com', 'Oleg'),
(6, 'roker', 'tsoy', 'tsoyisalive@gmail.com', 'Victor');

insert into team_member (team_id, user_id)
values
(2, 1), -- admint to dream team
(2, 2),
(4, 4), -- ivanov to NotDreamTeam
(5, 5),
(5, 6);

select * from team_member;

-- where clause
select * from users
where id > 2 and id < 6; -- and/or ~ &&/||

select * from users
where login = 'root';

select * from users
where login like 'root';

-- %
-- login like 'root%'; -- все строки, которые начинаются на 'root'
-- login like '%root'; -- все строки, которые заканчиваются на 'root'
-- login like '%root%'; -- все строки, которые содержат внутри 'root'

select * from users
where login like '%o%';

select * from users
where id != 2; -- <>/!= - не равно (аналог !=)

select * from users
where id in (1, 2, 6); -- id = 1 or id = 2 or id = 6

select * from users
where id not in (1, 2, 6); -- !(id = 1 or id = 2 or id = 6)

select * from users
where mod(id, 5) = 0;

select * from users
where cast (id as varchar) like '%1%';


-- Получить данные из нескольких таблиц

select * from team, team_member; -- декартовое произведение

select * from team t, team_member tm
where t.id = tm.team_id;  -- cross join - никогда-никогда не делать!
-- cross join
--	1. декартовое произведение
-- 	2. фильтрация по условию where

select * from team t
-- join
                  inner join team_member tm on t.id = tm.team_id;

select * from team t
                  left join team_member tm on tm.team_id = t.id
where tm.team_id is null;

-- симметрична
select * from team_member tm
                  right join team t on tm.team_id = t.id;




select * from team_member tm
                  left join team t on tm.team_id = t.id;

-- team
-- id/name
-- team_member
-- team_id/user_id

select team.* from team
                       left join team_member on id = team_id
where team_id is null;

-- Перенос данных из одной таблицы в другую
