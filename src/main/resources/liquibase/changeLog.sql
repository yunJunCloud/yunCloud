--liquibase formatted sql

--changeset myk:20220622
alter table `user` add column `delete` int(2) not null  default 0
--rollback select * from `user`;