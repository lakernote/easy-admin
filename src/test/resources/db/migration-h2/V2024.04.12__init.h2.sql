CREATE TABLE ext_leave (
                           leave_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           leave_day INT NULL DEFAULT NULL COMMENT '请假天数',
                           leave_reason VARCHAR(255) NULL DEFAULT NULL COMMENT '请假原因',
                           leave_user_id BIGINT NULL DEFAULT NULL COMMENT '请假人id',
                           create_by BIGINT NULL DEFAULT NULL COMMENT '创建人',
                           create_dept_id BIGINT NULL DEFAULT NULL COMMENT '创建人部门',
                           create_time TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
                           order_id VARCHAR(40) NULL DEFAULT NULL
);
CREATE TABLE sys_task (
                          task_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
                          task_code VARCHAR(255) NULL DEFAULT NULL COMMENT '任务的编码 必须全局唯一',
                          task_name VARCHAR(255) NULL DEFAULT NULL COMMENT '任务的名称',
                          task_class_name VARCHAR(255) NULL DEFAULT NULL COMMENT '任务的类名称',
                          task_cron VARCHAR(255) NULL DEFAULT NULL COMMENT '任务的cron表达式',
                          create_time TIMESTAMP NULL DEFAULT NULL COMMENT '任务创建时间',
                          enable TINYINT NULL DEFAULT NULL COMMENT '是否启用',
                          task_state INT NULL DEFAULT NULL COMMENT '任务状态',
                          UNIQUE (task_code)
);

CREATE TABLE sys_user (
                          user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          user_name VARCHAR(255) NULL DEFAULT NULL,
                          password VARCHAR(255) NULL DEFAULT NULL,
                          nick_name VARCHAR(255) NULL DEFAULT NULL,
                          dept_id BIGINT NULL DEFAULT NULL,
                          sex INT NULL DEFAULT NULL,
                          phone VARCHAR(255) NULL DEFAULT NULL,
                          enable INT NULL DEFAULT NULL,
                          email VARCHAR(255) NULL DEFAULT NULL,
                          avatar VARCHAR(255) NULL DEFAULT NULL,
                          create_time TIMESTAMP NULL DEFAULT NULL,
                          UNIQUE (user_name)
);
