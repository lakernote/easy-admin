# easy-admin

#### 介绍

**easy-admin：**       **easy**，**easy**，**easy**，打造一个**简单**、**轻量级**的**后台管理系统脚手架**。目前使用的技术都是相对较轻量级、上手很容易的技术。例如：**Spring Boot**、**hutool-all**、**mybatis-plus** 、**knife4j**  、**sa-token**、**javamelody** 、**snakerflow** 等。**后续的发展方向也是把目标对准中小型项目**，**提炼简单高效架构**。

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

| 功能                                           | 完成情况 |
| ---------------------------------------------- | -------- |
| **用户管理、部门管理、菜单管理、角色管理**     | ✅        |
| **基于RBAC角色的访问控制**                     | ✅        |
| **基于knife4j-Api文档集成**                    | ✅        |
| **基于javamelody应用监控**                     | ✅        |
| **基于Snakerflow的工作流引擎**                 | ✅        |
| **基于mybatis插件的数据权限控制**              | ❎        |
| **基于SpringTask定时任务（支持动态CRUD任务）** | ❎        |

##### 技术选型

**前端**：

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

#### 安装教程

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

![](https://img-blog.csdnimg.cn/460b237c269f48fba2b49633f094cc76.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2FidTkzNTAwOTA2Ng==,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/9dee2724dc4c4befac3e5bb983fd1726.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2FidTkzNTAwOTA2Ng==,size_16,color_FFFFFF,t_70)



![](https://img-blog.csdnimg.cn/1c01801cbd434150bb70890be31084d1.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2FidTkzNTAwOTA2Ng==,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/60775442c9034987a9c55c2447856c2f.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2FidTkzNTAwOTA2Ng==,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/4c016aeead7a44d6a22db8cd14db35a9.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2FidTkzNTAwOTA2Ng==,size_16,color_FFFFFF,t_70)











