create table if not exists person  (id int, name varchar(50)) ;
alter table person add column if not exists age int;

delete from person;

insert into person (id, name, age)
SELECT
    a.n, 'pete', random() * 100 + 1
FROM
    generate_series(1,10000) as  a(n);