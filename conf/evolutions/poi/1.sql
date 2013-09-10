# POi database schema  - 1.sql
# ~~~~~

# --- !Ups

create table users (
    id                          bigint auto_increment not null primary key,
    email                       varchar(255) not null,
    name                        varchar(255) not null,
    password                    varchar(255) not null,
    last_login_date             timestamp
);

create table poi_categories (
    id                          bigint auto_increment not null primary key,
    name                        varchar(255) not null,
    image_path                  varchar(255)
);

create table votes (
    id                          bigint auto_increment not null primary key,
    user_id                     bigint not null,
    external_poi_id             varchar(255) not null,
    poi_cat_id                  bigint not null,
    vote                        boolean,
    date_voted                  timestamp not null,
    foreign key(poi_cat_id)     references poi_categories(id),
    foreign key(user_id)        references users(id)
);


# --- !Downs

drop table if exists users;
drop table if exists poi_categories;
drop table if exists votes;

