--add dic code
INSERT INTO `code`(`code`,`parentId`,`value`,`order`,`desc`,`create_date`,`update_date`)
VALUES (100,0,'用户状态',0,'用户状态',NOW(),NOW()),
       (1001,100,'正常',1,'用户状态',NOW(),NOW()),
       (1002,100,'停用',2,'用户状态',NOW(),NOW()),
       (1003,100,'锁定',3,'用户状态',NOW(),NOW());


--add user
INSERT INTO `user`(`name`,`nick_name`,`password`,`phone`,`status`,`is_delete`,`create_date`,`update_date`,`version`)
VALUES ('jack','jack','12345678','123456',0,'1',NOW(),NOW())