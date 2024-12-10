pipeline {
    agent any
    environment {
        // 环境变量，可以方便管理
        imageName = "laker-test:latest"
        healthCheckUrl = "http://10.2.2.2:8089/actuator/health"
        dbUser = 'root'
        dbPassword = '123456'
        dbUrl = 'jdbc:mysql://10.2.2.2:3306/easy-admin?serverTimezone=GMT%2B8&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true'
    }
    tools {
        // 指定在 Jenkins 上配置的 Maven 工具名
        maven 'Maven-3.9.9' // Maven 配置名称，根据你在 Jenkins 配置中定义的工具名来调整
    }
    stages {
        stage('Checkout') {
            steps {
                // 从Gitee仓库获取代码   git 'https://gitee.com/lakernote/easy-admin.git'
                git branch: 'springboot3', url: 'https://gitee.com/lakernote/easy-admin.git'

            }
        }

        stage('Build') {
            steps {
                // 输出环境变量供调试使用
                sh 'echo $MAVEN_HOME'
                sh 'echo $PATH'
                sh "java -version"
                // 使用 Maven 构建项目
                sh 'mvn clean package -DskipTests' // 跳过测试，加速构建
            }
        }

        stage('Build Docker Image') {
            steps {
                // 构建 Docker 镜像
                script {
                    sh "docker build -t ${imageName} ."
                }
            }
        }
        stage('Run Docker Container') {
            steps {
                script {
                    // 停止并删除可能已存在的容器，避免冲突
                    sh '''
                    if [ "$(docker ps -aq -f name=laker-test)" ]; then
                        docker stop laker-test || true
                        docker rm laker-test || true
                    fi
                    '''

                    // 启动新的容器
                    sh """
                        docker run -d --name laker-test \
                        -e SPRING_DATASOURCE_USERNAME='${dbUser}' \
                        -e SPRING_DATASOURCE_PASSWORD='${dbPassword}' \
                        -e SPRING_DATASOURCE_URL='${dbUrl}' \
                        -p 8089:8080 ${imageName}
                    """
                }
            }
        }

        stage('Health Check') {
            steps {
                script {
                    def maxRetries = 5
                    def waitTime = 4 // seconds

                    echo "Health Check URL: ${healthCheckUrl}"

                    for (int i = 0; i < maxRetries; i++) {
                        try {
                            echo "Health Check Attempt ${i + 1}: Checking ${healthCheckUrl}..."
                            def status = sh(
                                    script: "curl -s -o /dev/null -w '%{http_code}' ${healthCheckUrl}",
                                    returnStdout: true
                            ).trim()

                            echo "HTTP Status Code: ${status}"

                            if (status == '200') {
                                echo "Health check passed!"
                                break
                            } else {
                                echo "Health check failed with status ${status}. Retrying in ${waitTime} seconds... (${i + 1}/${maxRetries})"
                            }
                        } catch (Exception e) {
                            echo "Error during health check: ${e.getMessage()}"
                        }

                        if (i < maxRetries - 1) {
                            sleep(waitTime)
                        } else {
                            error "Health check failed after ${maxRetries} retries."
                        }
                    }
                }
            }
        }

    }
    post {
        always {
            // 清理工作
            echo 'Pipeline 执行完成，开始清理工作...'
            sh 'docker image prune -f'
            sh 'docker system prune -a -f'
            echo '清理工作完成！'
        }
        success {
            echo 'Pipeline 执行成功！'
        }
        failure {
            echo 'Pipeline 执行失败，请检查日志。'
        }
    }
}