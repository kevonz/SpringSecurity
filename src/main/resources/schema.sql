delimiter $$

CREATE DATABASE `springsecurity3` /*!40100 DEFAULT CHARACTER SET utf8 */$$

delimiter $$

CREATE TABLE `sys_authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `issys` bit(1) DEFAULT NULL,
  `module` varchar(4) DEFAULT NULL COMMENT '所属的子系统，比如平台里面包括10个系统，分别为成本、作业、集输等。',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='权限表'$$


delimiter $$

CREATE TABLE `sys_authority_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `authority_id` int(11) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='权限资源表'$$


delimiter $$

CREATE TABLE `sys_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `type` varchar(40) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `issys` bit(1) DEFAULT NULL,
  `module` varchar(4) DEFAULT NULL COMMENT '所属的子系统，比如平台里面包括10个系统，分别为成本、作业、集输等。',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='资源表'$$


delimiter $$

CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `issys` bit(1) DEFAULT NULL,
  `module` varchar(4) DEFAULT NULL COMMENT '所属的子系统，比如平台里面包括10个系统，分别为成本、作业、集输等。',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色表'$$


delimiter $$

CREATE TABLE `sys_role_authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `authority_id` int(11) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色权限表'$$


delimiter $$

CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL COMMENT '加密的，格式为password{username}。如用户的密码为pwd，用户名为user，那么通过MD5进行加密的串为： pwd{user}',
  `enabled` bit(1) DEFAULT NULL,
  `issys` bit(1) DEFAULT NULL,
  `module` varchar(4) DEFAULT NULL COMMENT '所属的子系统，比如平台里面包括10个系统，分别为成本、作业、集输等。',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户表'$$


delimiter $$

CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色权限表'$$


