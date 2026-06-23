# Easyadmin
- **Gitee**：[https://gitee.com/lakernote/easy-admin](https://gitee.com/lakernote/easy-admin)
- **Github**：[https://github.com/lakernote/easy-admin](https://github.com/lakernote/easy-admin)

## 🚀 新版本推荐：EasyNextAdmin

> EasyNextAdmin 是 EasyAdmin 的新一代前后端分离版本，采用 Spring Boot 3 + Vue 3，面向 AI Vibe Coding 和企业后台二次开发做了更清晰的模块边界、权限契约和文档入口。
>
> - **Gitee**：[https://gitee.com/lakernote/easy-next-admin](https://gitee.com/lakernote/easy-next-admin)
> - **Github**：[lakernote/easy-next-admin](https://github.com/lakernote/easy-next-admin)

 **推荐我的另一个开源项目** 
> 🚀 一款高仿 Postman + 简易版 JMeter 的开源接口调试与压测工具，专为开发者优化，界面简洁、功能强大。

> [https://gitee.com/lakernote/easy-postman](https://gitee.com/lakernote/easy-postman)

> **有用的话请鼓励❤️下作者，右上角☝️watch、star、fork三连点🙏🙏🙏一波**

| SpringBoot版本 | JDK版本 | Mysql  | Tomcat | 分支                                                         |
|--------------| ------- | ------ | ------ | ------------------------------------------------------------ |
| 2.3.xx       | 8       | 5.7.xx | 9.x    | [master](https://gitee.com/lakernote/easy-admin/tree/master/) |
| 3.2.xx       | 17      | 8.xx   | 10.x   | [springboot3](https://gitee.com/lakernote/easy-admin/tree/springboot3/) |

#### 🌵介绍

基于 **SpringBoot2（JDK1.8）+MybatiPlus+LayUI+Snaker+Mysql5.7**技术，可**前后端分离开发**或者 **前后端一体式开发** ，一款**简单**、**轻量级**的 **后台管理系统脚手架** 。 **内置权限管理、工作流引擎、应用监控、Api文档、行为监控、前后端代码生成、动态定时任务、在线WebLog等** 。可以理解为 **轻量、升级版的若依/RuoYi** 。

**相比于若依优势如下**：

- **若依有的功能EasyAdmin都有，但是使用简洁、有效的轻量级实现，更少的依赖，更低的学习成本**
- **若依缺乏的常用功能EasyAdmin也添加了，都是从我实际接触的项目提炼而来，例如：工作流、在线WebLog、应用监控、NginxUI等**
- **EasyAdmin具有更全面的配套文档，这个项目也是由配套专栏[《从零搭建开发脚手架》](https://blog.csdn.net/abu935009066/category_10817814.html)整理而来**

> 这里没有说若依不好，若依是非常优秀的开源项目，我也是若依项目的使用者之一，**EasyAdmin**是我**根据工作中**很多**实际项目**锤炼出的一套非常适合**中小型**企业开发的框架，简单来说**EasyAdmin是杀鸡刀**，**若依是杀牛刀**，用户名可以根据具体的需求场景进行选择，莫喷、莫喷、莫喷。

**愿景**是打造一款**简单**、**轻量级**的**后台管理系统脚手架**⛳⛳⛳。目前使用的技术都是相对较轻量级、上手很容易的技术。例如：**Spring Boot**、**hutool-all**、**mybatis-plus** 、**knife4j**  、**sa-token**、**javamelody** 、**snakerflow** 等。**后续的发展方向也是把目标对准中小型项目**，**提炼简单高效架构**。

**适合场景：💋学生毕设学习、💋前后端项目练手、💋私活快速开发、💋中小型企业脚手架、💋Spring Boot深度扩展学习**等

**项目架构灵活多变**，**开发模式**支持**前后端分离**和**不分离**模式，**部署模式支持多种方式**：**Fat.jar模式**、**Nginx反向代理**、**Nginx正向代理**。

**项目地址**：[https://gitee.com/lakernote/easy-admin](https://gitee.com/lakernote/easy-admin)

📕**配套技术文章**：👉[从零搭建开发脚手架](https://blog.csdn.net/abu935009066/category_10817814.html)

> 本开源项目，也是从我的专栏《从零搭建开发脚手架》整理而来

📗**配套视频地址**：👉[B站欢迎一键三连](https://space.bilibili.com/502919442)

> 视频正在实时更新中，欢迎大家批评指导



#### 🌰 在线演示

🌽**地址**：[http://101.132.189.23:81/admin/login.html](http://101.132.189.23:81/admin/login.html)

> **低配机轻点拍**，**穷比**✈️✈️✈️✈️✈️✈️✈️

- **用户名/密码**：**laker**/**lakernote**（**普通员工**-老李提交请假申请）

- **用户名/密码**：**yang**/**lakernote**（**部门领导**-杨总审批）

- **用户名/密码**：**zhang**/**lakernote**（**公司领导**-大于2天张总审批）

>  **当前处于开发阶段** ，由于我前端技术小白水平，开发进度较慢，还有很多功能未开发完成，有想一起开发的小伙伴，请加微信☎️【**lakernote**】联系我。

#### 🌴 功能列表

| 功能                                           | 状态 | 相关文档                                                     |
| ---------------------------------------------- | -- | ------------------------------------------------------------ |
| **用户管理、部门管理、菜单管理、角色管理** 🐾   | ✅  |                                                              |
| **基于RBAC角色的访问控制** 🐾                   | ✅  | [认证授权 sa-token](https://blog.csdn.net/abu935009066/article/details/115553517) |
| **基于knife4j-Api文档集成** 🐾                  | ✅  | [Knife4j替换swagger](https://blog.csdn.net/abu935009066/article/details/115512988) |
| **基于javamelody应用监控** 🐾                   | ✅  | [Javamelody-应用程序监控](https://blog.csdn.net/abu935009066/article/details/116936366) |
| **基于Snakerflow的工作流引擎** 🐾               | ✅  | [轻量级工作流引擎Snakerflow集成](https://blog.csdn.net/abu935009066/article/details/119568513) |
| **自定义注解+AOP用户行为分析** 🐾               | ✅  | [SpringBoot自定义注解+AOP实现用户行为监控](https://blog.csdn.net/abu935009066/article/details/119755927) |
| **基于Freemrker的前后端代码一键生成** 🐾        | ✅  |                                                              |
| **基于mybatis插件的数据权限控制** 🐾            | ✅  | [基于Mybatis-Plus的数据权限实现](https://blog.csdn.net/abu935009066/article/details/115481149) |
| **基于SpringTask定时任务(支持动态CRUD任务)** 🐾 | ✅  | [基于Spring Task实现动态管理任务](https://blog.csdn.net/abu935009066/article/details/116142630) |
| **在线WebLog、动态修改日志级别** 🐾             | ✅  | [在线WebLog、动态修改日志级别](https://blog.csdn.net/abu935009066/article/details/114121941) |
| **保证服务的幂等性和防止重复请求** 🐾           | ✅  | [相关文档](https://blog.csdn.net/abu935009066/article/details/117471885) |
| **独特的数据权限过滤功能** 🐾                   | ✅  |                                                              |
| **前端路由守卫**                               | ❌   |                                                              |
| **前端按钮控制**                               | ❌   |                                                              |

#### 🌲软件架构

**其他相关文章**

- [从零搭建开发脚手架 HttpServletRequest多次读取异常，仅能读取一次](https://blog.csdn.net/abu935009066/article/details/113870578)
- [从零搭建开发脚手架 Spring Boot 输入参数校验多种方式整理](https://blog.csdn.net/abu935009066/article/details/114001409)
- [从零搭建开发脚手架 实现在线WebLog、动态修改日志级别](https://blog.csdn.net/abu935009066/article/details/114121941)
- [跨站请求伪造（CSRF）示例、原理及其防御措施](https://blog.csdn.net/abu935009066/article/details/114366771)
- [从零搭建开发脚手架 Spring Boot集成Mybatis-plus之一](https://blog.csdn.net/abu935009066/article/details/114535661)
- [从零搭建开发脚手架 Spring Boot集成Flyway实现数据库版本管理](https://blog.csdn.net/abu935009066/article/details/114586037)
- [Spring Boot Tomcat临时目录tmp抛错误异常](https://blog.csdn.net/abu935009066/article/details/114596193)

##### 🌳技术选型

**前端**：

**底座是Layui**

- https://gitee.com/pear-admin/Pear-Admin-Layui
- https://gitee.com/zhongshaofa/layuimini

> 这里重点感谢Layui、Pear-Admin-Layui、layuimini，我一个前端小白，学习一周就能撸出来EasyAdmin了。

**后端**

- **Spring Boot 2.3.7.RELEASE**
- **hutool-all 工具类**
- **lombok**
- **mybatis-plus 3.4.2**
- **druid 数据库连接池**
- **mysql 数据库**
- **knife4j  Api文档**
- **sa-token 认证授权**
- **javamelody 应用监控**
- **easyexcel  Excel处理**
- **mail 邮箱**
- **snakerflow 国产工作流引擎**

#### 🌼开发教程

##### 服务端

**1.执行**`sql/easy-admin.sql`

**2.修改**`src/main/resource/application.yaml`

```yaml
server:
  port: 8080
spring:
  datasource:
    username: root
    password: 123456
    url: jdbc:mysql://localhost:3306/laker?serverTimezone=GMT%2B8&characterEncoding=utf8&useSSL=false
```

**3.运行**`EasyAdminApplication.java`

> windows系统会自动在浏览器打开首页。超级管理员用户名密码：admin/ilovelaker

##### 前端

> 有2种方式供选择

**方式一、Spring Boot虚拟磁盘模式**

运行`EasyAdminApplication.java`会自动在浏览器打开首页。**属于一体化开发模式**。

**方式二、纯静态模式**

纯静态的，可直接在浏览器运行，**属于前后端分离开发模式**。

1.修改配置`web/admin/config/pear.config.yml`

```javascript
## 配置服务端地址
admin:
  server: http://localhost:8080
```

2.在`web/admin/index.html`处，如下图示例操作，点击图标就可以在浏览器访问了

![](https://oscimg.oschina.net/oscnet/up-9a401367595b08fbef9dbf5d1c8fbb1357f.png)

#### 🍄代码生成

执行`CodeGenerator.java`，按照提示输入即可，以下为示例和解释

```
--------生成文件输出目录---------
D:\JT\easy-admin/src/main/java
-----------------
请输入模块名：
module.ext  // 仅仅会影响生成java代码路径D:\JT\easy-admin/src/main/java/com/laker/admin/module/ext
请输入表名，多个英文逗号分割：
ext_log    // 影响前端代码生成路径D:\JT\easy-admin/web/admin/view/ext/log
           // 影响接口定义 http://xx:port/ext/log
```

生成好后，直接在菜单权限处，配置访问路径`view/ext/log.html`即可访问新建模块

#### 🍂规约

数据库表命名：模块名+实体，例如：ext_log

数据库字段：主键定义为bigint，且命名为：业务Id,例如：logId

####  🍃项目截图

**在线WebLog**

![](https://images.gitee.com/uploads/images/2021/0813/164746_e1d2e656_709188.gif)



![](https://oscimg.oschina.net/oscnet/up-41ea19b9eef85113b393cb3f9d0e1d81fa8.png)

![](https://oscimg.oschina.net/oscnet/up-21e98010b5ca601657e1da49dc0024e4b4d.png)

![](https://oscimg.oschina.net/oscnet/up-0b3a7e263e7f84f824b6e4a653504064f9d.png)

#### 🌺部署教程

1.执行`mvn clean package`，会生成一个包含前后端和、配置、启动脚本的ZIP.

> 目录为：`target/easyAdmin-local-1.0.0.zip`

**easyAdmin.zip目录结构如下：**

![](https://oscimg.oschina.net/oscnet/up-ef25775de080151e89c785ca97282680b29.png)

##### 服务端

**1.执行**`easy-admin.sql`

**2.修改**`application-{profile}.yaml`

```yaml
server:
  port: 8080
spring:
  datasource:
    username: root
    password: 123456
    url: jdbc:mysql://localhost:3306/laker?serverTimezone=GMT%2B8&characterEncoding=utf8&useSSL=false
```

**3.启动**

```sh
nohup java -jar easy-admin-1.0.0.jar &
```

> 由于验证码涉及到java安全协议，建议使用脚本启动 `sh run.sh start`

##### 前端

**首先修改配置**`web/admin/config/pear.config.yml`，填写你自己服务器实际ip、port

```javascript
## 配置服务端地址
admin:
  server: http://localhost:8080
```

> 也可以把前端放在Nginx中。

##### 校验

**浏览器访问**`http://ip:port/admin`

> **如果部署失败请查看**[Easyadmin部署及部署常见问题解决](https://blog.csdn.net/abu935009066/article/details/120529971)，**或者联系作者**。

###  ☎️联系方式☎️

**个人微信: lakernote**（进群加我拉你，备注：easyadmin）

![](https://oscimg.oschina.net/oscnet/up-6c2d9e46db42d4464e4d2f2a06975f533cf.png)


------------------------------------------------

**微信公众号** : **李哥聊架构**

![](https://oscimg.oschina.net/oscnet/up-fadd81a96306d5a18f245dcd44d7d545b8a.png)
