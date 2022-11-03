# 该镜像需要依赖的基础镜像
FROM openjdk:8-jre
# 指定维护者的名字
MAINTAINER laker "935009066@qq.com"
# 使用东八区时间环境
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
# 将指定目录下的jar包复制到docker容器的/目录下
COPY /target/*.jar /app.jar
COPY /web /web
# 声明服务运行在8080端口
EXPOSE 8080
# 指定docker容器启动时运行jar包
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]