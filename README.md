# easy-admin

#### 介绍

**easy-admin：**       **easy**，**easy**，**easy**，打造一个**简单**、**轻量级**的**后台管理系统脚手架**。目前使用的技术都是相对较轻量级、上手很容易的技术。例如：**Spring Boot**、**hutool-all**、**mybatis-plus** 、**knife4j**  、**sa-token**、**javamelody** 、**snakerflow** 等。**后续的发展方向也是把目标对准中小型项目**，**提炼简单高效架构**。

**适合场景：**`学生学习`、`前后端项目练手`、`接私活快速开发`、`中小型企业脚手架`、`Spring Boot深度扩展学习`等

**项目地址**：[https://gitee.com/lakernote/easy-admin](https://gitee.com/lakernote/easy-admin)

**配套技术文章**：[从零搭建开发脚手架](https://blog.csdn.net/abu935009066/category_10817814.html)

> 本开源项目，也是从我的专栏《从零搭建开发脚手架》整理而来

##### 联系方式

**微信公众号** : **Java大厂面试官** , **个人微信: lakernote**
![img](https://img-blog.csdnimg.cn/2020110915544650.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2FidTkzNTAwOTA2Ng==,size_16,color_FFFFFF,t_70#pic_center)

#### 在线演示

暂时无服务器。。。尬住了

#### 软件架构

##### 功能列表

| 功能                                           | 完成情况 | 相关文档                                                     |
| ---------------------------------------------- | -------- | ------------------------------------------------------------ |
| **用户管理、部门管理、菜单管理、角色管理**     | ✅        |                                                              |
| **基于RBAC角色的访问控制**                     | ✅        | [认证授权 sa-token](https://blog.csdn.net/abu935009066/article/details/115553517) |
| **基于knife4j-Api文档集成**                    | ✅        | [Knife4j替换swagger](https://blog.csdn.net/abu935009066/article/details/115512988) |
| **基于javamelody应用监控**                     | ✅        | [Javamelody-应用程序监控](https://blog.csdn.net/abu935009066/article/details/116936366) |
| **基于Snakerflow的工作流引擎**                 | ✅        | [轻量级工作流引擎Snakerflow集成](https://blog.csdn.net/abu935009066/article/details/119568513) |
| **基于mybatis插件的数据权限控制**              | ❎        | [基于Mybatis-Plus的数据权限实现](https://blog.csdn.net/abu935009066/article/details/115481149) |
| **基于SpringTask定时任务（支持动态CRUD任务）** | ❎        | [基于Spring Task实现动态管理任务](https://blog.csdn.net/abu935009066/article/details/116142630) |
| **在线WebLog、动态修改日志级别**               | ✅        | [在线WebLog、动态修改日志级别](https://blog.csdn.net/abu935009066/article/details/114121941) |

**其他相关文章**

- [从零搭建开发脚手架 HttpServletRequest多次读取异常，仅能读取一次](https://blog.csdn.net/abu935009066/article/details/113870578)
- [从零搭建开发脚手架 Spring Boot 输入参数校验多种方式整理](https://blog.csdn.net/abu935009066/article/details/114001409)
- [从零搭建开发脚手架 实现在线WebLog、动态修改日志级别](https://blog.csdn.net/abu935009066/article/details/114121941)
- [跨站请求伪造（CSRF）示例、原理及其防御措施](https://blog.csdn.net/abu935009066/article/details/114366771)
- [从零搭建开发脚手架 Spring Boot集成Mybatis-plus之一](https://blog.csdn.net/abu935009066/article/details/114535661)
- [从零搭建开发脚手架 Spring Boot集成Flyway实现数据库版本管理](https://blog.csdn.net/abu935009066/article/details/114586037)
- [Spring Boot Tomcat临时目录tmp抛错误异常](https://blog.csdn.net/abu935009066/article/details/114596193)

##### 技术选型

**前端**：

**底座是Layui**

- https://gitee.com/pear-admin/Pear-Admin-Layui

- https://gitee.com/zhongshaofa/layuimini

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

#### 开发教程

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

##### 前端

2种方式部署

**一、Spring Boot虚拟磁盘模式**

运行`EasyAdminApplication.java`会自动在浏览器打开首页。**推荐**

**二、纯静态模式**

> 测试中

纯静态的，可直接在浏览器运行，修改配置`web/admin/componet/pear/pear.js`

```javascript
const EasyAdminContext = {
    url: "http://localhost:8080"
};
```

直接在浏览器访问`web/admin/index.html`

#### 项目截图

**在线WebLog**

![](https://images.gitee.com/uploads/images/2021/0813/164746_e1d2e656_709188.gif)

**登录页**

![](https://img-blog.csdnimg.cn/460b237c269f48fba2b49633f094cc76.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2FidTkzNTAwOTA2Ng==,size_16,color_FFFFFF,t_70)

**主页菜单**

![](https://img-blog.csdnimg.cn/9dee2724dc4c4befac3e5bb983fd1726.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2FidTkzNTAwOTA2Ng==,size_16,color_FFFFFF,t_70)

**流程定义**

![](https://img-blog.csdnimg.cn/1c01801cbd434150bb70890be31084d1.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2FidTkzNTAwOTA2Ng==,size_16,color_FFFFFF,t_70)

**应用监控**

![](https://img-blog.csdnimg.cn/60775442c9034987a9c55c2447856c2f.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2FidTkzNTAwOTA2Ng==,size_16,color_FFFFFF,t_70)

**接口文档**

![](https://img-blog.csdnimg.cn/4c016aeead7a44d6a22db8cd14db35a9.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2FidTkzNTAwOTA2Ng==,size_16,color_FFFFFF,t_70)



#### 部署教程

整体部署包位置如下：




![](https://images.gitee.com/uploads/images/2021/0812/141324_9e6528a0_709188.png "屏幕截图.png")

##### 服务端

> mvn clean package 打包

**1.执行**`easy-admin.sql`

**2.修改**`application.yaml`

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
nohup java -jar easy-admin.jar &
```

##### 前端

**按照相对位置放即可**

```
easy-admin.jar
application.yml
web
--admin
----admin
----compoment
----config
----view
----index.html
----login.html
```

浏览器访问`http://ip:port/admin`