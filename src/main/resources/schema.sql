create table hero
(
    id         SERIAL primary key,
    hero_name  varchar(64)   not null,
    strength   int default 0 not null,
    speed      int default 0 not null,
    durability int default 0 not null,
    power      varchar(100)  not null,
    birthdate  timestamp     not null,
    unique (hero_name)
);