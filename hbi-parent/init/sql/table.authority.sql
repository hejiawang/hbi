/* 用户信息表 */
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(36) NOT NULL COMMENT '主键uuid',
  `LOGIN_NAME` varchar(50) NOT NULL COMMENT '登录名',
  `PASSWORD` varchar(255) NOT NULL COMMENT '登录密码',
	`REAL_NAME` varchar(255) DEFAULT NULL COMMENT '真实姓名',
	`TELEPHONE` varchar(11) DEFAULT NULL COMMENT '联系电话',
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;
