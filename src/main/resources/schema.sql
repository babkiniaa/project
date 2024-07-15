create schema if not exists yara_sch;

create table if not exists yara_sch.products1
(
    id_product       int primary key,
    name_product     varchar(255) not null
);
