# Easyadmin

> **有用的话请鼓励❤️下作者，右上角☝️watch、star、fork三连点🙏🙏🙏一波**

#### 🌵介绍

这个分支大量精力放在**企业级解决方案**上，A tool not a toy.
> 正在迭代中，里面的组件后面我尽量选用**企业级别**的，**剔除一些国产组件**。
>
> 重点是服务端，前端仅作为一个展示窗口
> - redis
> - kafka
> - elasticsearch
> - cassandra
> - influxdb
> - pinot
> - grafana
> - elk
> - docker
> - docker-compose

##### mysql

```shell
docker run -p 3306:3306 --name mysql8 \
-e MYSQL_ROOT_PASSWORD=123456 \
-e MYSQL_DATABASE=easy-admin \
-e MYSQL_USER=easy-admin \
-e MYSQL_PASSWORD=123456 \
-d mysql:8.3 --default-authentication-plugin=mysql_native_password
```

##### kafka

```shell
docker run -d \
  --name kafka37 \
  -p 9092:9092 \
  -e KAFKA_CFG_AUTO_CREATE_TOPICS_ENABLE=false \
  -e KAFKA_CFG_NODE_ID=0 \
  -e KAFKA_CFG_PROCESS_ROLES=controller,broker \
  -e KAFKA_CFG_CONTROLLER_QUORUM_VOTERS=0@localhost:9093 \
  -e KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP=CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT \
  -e KAFKA_CFG_LISTENERS=PLAINTEXT://0.0.0.0:9092,CONTROLLER://:9093 \
  -e KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://10.12.22.9:9092 \
  -e KAFKA_CFG_CONTROLLER_LISTENER_NAMES=CONTROLLER \
  bitnami/kafka:3.7
```

##### Redis

```shell
docker run -d \
  --name redis6 \
  -p 6379:6379 \
  redis:6-alpine

// 将 yourpassword 替换为您想要设置的密码。  
docker run -d \
  --name redis6 \
  -p 6379:6379 \
  redis:6-alpine \
  redis-server --requirepass yourpassword
  
// 将 yourpassword 替换为您想要设置的密码。首先创建一个 redis.conf 文件，并包含以下内容：
requirepass yourpassword
// 然后，运行以下命令：
docker run -d \
  --name redis6 \
  -p 6379:6379 \
  -v /path/to/redis.conf:/usr/local/etc/redis/redis.conf \
  redis:6-alpine redis-server /usr/local/etc/redis/redis.conf
```

##### InfluxDB

```shell
docker run -d \
--name influxdb18 \
-p 8086:8086 \
-e INFLUXDB_ADMIN_USER=admin \
-e INFLUXDB_ADMIN_PASSWORD=123456 \
-e INFLUXDB_USER=easy-admin \
-e INFLUXDB_USER_PASSWORD=123456 \
-v /etc/localtime:/etc/localtime \
influxdb:1.8
``` 

##### Grafana

默认账号密码：admin/admin
https://grafana.com/grafana/dashboards/ 所有模板
16107

```shell
docker run -d -p 3000:3000 grafana/grafana
```