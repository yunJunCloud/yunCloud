--init user
CREATE TABLE IF NOT EXISTS `user` (
                        `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
                        `name` varchar(64) NOT NULL COMMENT '用户名称',
                        `nick_name` varchar(64)  COMMENT '用户昵称',
                        `password` varchar(64) NOT NULL COMMENT '用户密码',
                        `phone` varchar(11)  COMMENT '手机号',
                        `status` int NOT NULL DEFAULT '0' COMMENT '用户状态',
                        `is_delete` int NOT NULL DEFAULT '0' COMMENT '删除标志',
                        `create_date` datetime NOT NULL COMMENT '创建时间',
                        `update_date` datetime NOT NULL COMMENT '修改时间',
                        `version` bigint DEFAULT NULL COMMENT '版本号',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--init role

CREATE TABLE IF NOT EXISTS `role` (
                        `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
                        `role_name` varchar(50) NOT NULL COMMENT '角色名称',
                        `desc` varchar(200) NOT NULL COMMENT '角色描述',
                        `is_delete` int NOT NULL DEFAULT '0' COMMENT '删除标志',
                        `create_date` datetime NOT NULL COMMENT '创建时间',
                        `update_date` datetime NOT NULL COMMENT '修改时间',
                        `version` bigint DEFAULT NULL COMMENT '版本号',
                        PRIMARY KEY (`id`),
                        UNIQUE INDEX `unique_role_name`(`role_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--init permission

CREATE TABLE IF NOT EXISTS `permission` (
                        `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
                        `code` varchar(50) NOT NULL COMMENT '权限标识符',
                        `desc` varchar(200) NOT NULL COMMENT '权限描述',
                        `url` varchar(200) NOT NULL COMMENT '请求地址',
                        `is_delete` int NOT NULL DEFAULT '0' COMMENT '删除标志',
                        `create_date` datetime NOT NULL COMMENT '创建时间',
                        `update_date` datetime NOT NULL COMMENT '修改时间',
                        `version` bigint DEFAULT NULL COMMENT '版本号',
                         PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--init user_role

CREATE TABLE IF NOT EXISTS `user_role` (
                        `user_id` int NOT NULL COMMENT '用户id',
                        `role_id` int NOT NULL COMMENT '角色id',
                        `is_delete` int NOT NULL DEFAULT '0' COMMENT '删除标志',
                        `create_date` datetime NOT NULL COMMENT '创建时间',
                        `update_date` datetime NOT NULL COMMENT '修改时间',
                        `version` bigint DEFAULT NULL COMMENT '版本号',
    PRIMARY KEY (`user_id`,`role_id`) USING BTREE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--init role_permission

CREATE TABLE IF NOT EXISTS `role_permission` (
                        `role_id` int NOT NULL COMMENT '角色id',
                        `permission_id` int NOT NULL COMMENT '权限id',
                        `is_delete` int NOT NULL DEFAULT '0' COMMENT '删除标志',
                        `create_date` datetime NOT NULL COMMENT '创建时间',
                        `update_date` datetime NOT NULL COMMENT '修改时间',
                        `version` bigint DEFAULT NULL COMMENT '版本号',
                PRIMARY KEY (`role_id`,`permission_id`) USING BTREE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--init 数据字典表 code

CREATE TABLE IF NOT EXISTS `code` (
                            `id` int NOT NULL AUTO_INCREMENT COMMENT '主键 自增',
                            `code` bigint NOT NULL COMMENT '码值id',
                            `parentId` bigint NOT NULL COMMENT '父codeid',
                            `value` varchar(100) NOT NULL COMMENT '码值中文',
                            `order` int DEFAULT NULL COMMENT '排序',
                            `desc` varchar(255) DEFAULT NULL COMMENT '码值描述',
                            `is_delete` int NOT NULL DEFAULT '0' COMMENT '删除标志',
                            `create_date` datetime NOT NULL COMMENT '创建时间',
                            `update_date` datetime NOT NULL COMMENT '修改时间',
                            `version` bigint DEFAULT NULL COMMENT '版本号',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;