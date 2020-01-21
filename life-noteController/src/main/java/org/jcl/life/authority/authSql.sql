/*
 * 用户表
 * 角色名和密码
 */
DROP TABLE IF EXISTS `Sys_User`;
CREATE TABLE `Sys_User`
(
    `id`       BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(200)    NOT NULL,
    `password` VARCHAR(200)    NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = INNODB
  CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

/*
 * 角色表 角色种类
 */
DROP TABLE IF EXISTS `Sys_Role`;
CREATE TABLE `Sys_Role`
(
    `id`   BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(200)    NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = INNODB
  CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

/*
 * 权限表
 */
DROP TABLE IF EXISTS `Sys_permission`;
CREATE TABLE `Sys_permission`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(200)    NOT NULL,
    `description` VARCHAR(200) DEFAULT NULL,
    `url`         VARCHAR(200)    NOT NULL,
    `pid`         BIGINT       DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = INNODB
  CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

DROP TABLE IF EXISTS `Sys_role_user`;
CREATE TABLE `Sys_role_user`
(
    `id`      BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT UNSIGNED NOT NULL,
    `role_id` BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = INNODB
  CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

DROP TABLE IF EXISTS `Sys_permission_role`;
CREATE TABLE `Sys_permission_role`
(
    `id`            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `role_id`       BIGINT UNSIGNED NOT NULL,
    `permission_id` BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = INNODB
  CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

insert into SYS_USER (id, username, password)
values (1, 'admin', 'admin');
insert into SYS_USER (id, username, password)
values (2, 'gelisi', 'chenglei');

insert into SYS_ROLE(id, name)
values (1, 'ROLE_ADMIN');
insert into SYS_ROLE(id, name)
values (2, 'ROLE_USER');

insert into SYS_ROLE_USER(user_id, role_id)
values (1, 1);
insert into SYS_ROLE_USER(user_id, role_id)
values (2, 2);

INSERT INTO `Sys_permission`
VALUES ('1', 'ROLE_HOME', 'home', '/', null),
       ('2', 'ROLE_ADMIN', 'ABel', '/admin', null);
INSERT INTO `Sys_permission_role`
VALUES ('1', '1', '1'),
       ('2', '1', '2'),
       ('3', '2', '1');