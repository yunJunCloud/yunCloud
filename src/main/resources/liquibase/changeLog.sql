--liquibase formatted sql

--changeset myk:20220622
alter table `user` add column `delete` int(2) not null  default 0
--rollback select * from `user`;

--changeset myk:20220624
alter table `user` CHANGE `delete` `is_delete` int(2) not null  default 0 ;
alter table `user` add column `create_date` DATE ;
alter table `user` add column `update_date` DATE ;
alter table `user` add column `version` NUMERIC(16,0) not null ;
--rollback select * from `user`;