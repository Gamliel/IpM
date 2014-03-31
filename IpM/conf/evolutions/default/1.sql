# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table server_data (
  id                        varchar(255) not null,
  conventional_name         varchar(255),
  host_name                 varchar(255),
  domain                    varchar(255),
  port                      integer not null,
  ip_address                varchar(255),
  constraint pk_server_data primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table server_data;

SET FOREIGN_KEY_CHECKS=1;

