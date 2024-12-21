--liquibase formatted sql
--changeset MrSony:create-users-tables splitStatements=false

create table users
(
    id       bigserial primary key,
    login    varchar not null,
    password varchar not null,
    deleted  boolean not null default false
);

create unique index users_login_uidx
    on users
    using btree(login);

create table permissions
(
    code varchar primary key not null
);

create table user_permissions
(
    user_id         bigint references users (id),
    permission_code varchar references permissions (code),
    primary key (user_id, permission_code)
);

insert into permissions(code)
values ('api_mobile'),
       ('full_access');

insert into users(login, password)
values ('admin', '$2a$10$iqHH0P4N0cp3kZYG9czQSuqDXTfvKBNW4aOMZIgXahmyjG7TvWrhi');
insert into user_permissions(user_id, permission_code)
select id, 'full_access'
from users
where login = 'admin';

create table refresh_sessions
(
    refresh_token varchar                      not null primary key,
    user_id       bigint references users (id) not null,
    scope         varchar                      not null,
    created_at    timestamp without time zone not null,
    expired_at    timestamp without time zone not null
);

create index refresh_sessions_user_id_idx
    on refresh_sessions
    using btree(user_id);

create index refresh_sessions_expired_at_idx
    on refresh_sessions
    using btree(expired_at);
