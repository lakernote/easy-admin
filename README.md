# easy-admin

#### 介绍

**easy**，**easy**，**easy**，打造一个**简单**、**轻量级**的后台管理系统脚手架。目前使用的技术都是相对较轻量级、上手很容易的技术。例如：**Spring Boot**、**hutool-all**、**mybatis-plus** 、**knife4j**  、**sa-token**、**javamelody** 、**snakerflow** 等。**后续的发展方向也是把目标对准中小型项目**，**提炼简单高效架构**。

#### 软件架构

##### 功能列表

- 基于RBAC权限-已完成
- knife4j-Api文档集成-已完成
- 基于javamelody应用监控-已完成 

##### 技术选型

**前端**：

- https://gitee.com/pear-admin/Pear-Admin-Layui

- https://gitee.com/zhongshaofa/layuimini

**后端**

- Spring Boot 2.3.7.RELEASE
- hutool-all 工具类
- lombok
- mybatis-plus 3.4.2 
- druid 数据库连接池
- mysql 数据库
- knife4j  Api文档
- sa-token 认证授权
- javamelody 应用监控
- easyexcel  Excel处理
- mail 邮箱
- snakerflow 国产工作流引擎

#### 安装教程

**服务端**

1.执行`sql/flow.sql`

2.修改`src/main/resource/application.yaml`

```yaml
server:
  port: 8080
spring:
  datasource:
    username: root
    password: 123456
    url: jdbc:mysql://localhost:3306/laker?serverTimezone=GMT%2B8&characterEncoding=utf8&useSSL=false
```

3.运行`EasyAdminApplication.java`

**前端**

纯静态的，可直接在浏览器运行，修改配置`web/Pear.../componet/pear/pear.js`

```javascript
const EasyAdminContext = {
    url: "http://localhost:8080"
};
```

直接在浏览器访问`web/Pear.../index.html`



#### 使用截图

