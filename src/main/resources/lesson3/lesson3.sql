
--
select * from users;
select * from team_member;
select * from team;

-- avg(), max(), min(), count(), sum()
select * from team
                  left join team_member on id = team_id;

select count(*) from team
                         left join team_member on id = team_id; -- count - количество записей в выборке

select count(team_id) from team
                               left join team_member on id = team_id;

-- count(*) отличается от count(team_id)
-- count(*) учитывает null записи
-- count(team_id) считает количество строк с team_id не равным null

select name, count(team_id) from team t
                                     left join team_member tm on tm.team_id = t.id
group by id;

insert into team_member (team_id, user_id)
values
(5, 1);

--  login, count(t.id)
-- distinct - оставить только уникальные значия (убрать дубликаты)
select * from users u
                  join team_member tm on tm.user_id = u.id
                  join team t on t.id = tm.team_id
where login = 'admin' or login = 'root' -- фильтрация данных из выборки
group by t.id
-- аналог where
having count(t.id) > 1; -- фильтрация - только для сгруппированных данных


-- serial
-- bigint not null "auto_increment"

-- sequence - последовательность
-- таблица, состоящая из одной колонки и одной строки

-- call <procedure name>

select currval('team_id_seq'::regclass);

select currval('team_id_seq'); -- получение текущего значения (без увеличения)
select nextval('team_id_seq'); -- следующее значение в последовательности
-- nextval - ++value <- префиксный инкремент
select setval('team_id_seq', 100);

-- create table v(
--	id serial
-- )

-- create table v (
--	id bigint not null default nextval('v_id_seq')
--)


create sequence user_id_sequence
    increment by 10
    start with 400;

-- 400, 410, 420 ...

insert into users
values
(nextval('user_id_sequence'), 'test', 'test123', 'test@email.com', 'Test account');

-- insert into team(name) values ('Test name')

alter table users
    alter column id set default nextval('user_id_sequence');

insert into users (login, password, email, name)
values
('shagbark', '123qwerty', 'dmitr.prot@gmail.com', 'Dmitry');

select * from users;

select currval('user_id_sequence');

