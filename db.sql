create database attendance;

use attendance;

CREATE TABLE `user`
(
  `id`        int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username`  varchar(30)  DEFAULT NULL COMMENT '用户名',
  `password`  varchar(30)  DEFAULT NULL COMMENT '密码',
  `name`      varchar(30)  DEFAULT NULL COMMENT '姓名',
  `no`        varchar(20)  DEFAULT NULL COMMENT '学号',
  `photo`     varchar(250) DEFAULT NULL COMMENT '照片',
  `isTeacher` int(11)      DEFAULT 0 comment '是否为老师',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `course`
(
  `id`        int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name`      varchar(30) DEFAULT NULL,
  `className` varchar(30) DEFAULT NULL,
  `teacherId` int(11),
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `sc`
(
  `id`        int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `studentId`      int(11),
  `classId` int(11),
  `remarks` varchar(255) default null,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;