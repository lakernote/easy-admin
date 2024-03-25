# 该镜像需要依赖的基础镜像
FROM openjdk:8-jre
# 指定维护者的名字
MAINTAINER laker "935009066@qq.com"
# 创建目录
RUN mkdir -p /laker
# 设置为工作目录，等于cd /laker
WORKDIR /laker
# 使用东八区时间环境
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
# 将指定目录下的jar包复制到docker容器的/目录下
COPY target/*.jar app.jar
# 复制前端web到容器内的 /laker/web
COPY /web /laker/web
# 复制存储storage到容器内的 /laker/storage
COPY /storage /laker/storage
# 暴露8080端口
EXPOSE 8080
# 指定docker容器启动时运行jar包
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]

# 执行 docker build -t easy-admin . 构建镜像easy-admin
# 执行 docker run -d -p 8080:8080 easy-admin 启动容器