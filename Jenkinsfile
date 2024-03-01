pipeline {
    // 在任何代理节点运行
    agent any

    tools {
        // maven3.6.0 要在 jenkins全局工具配置中提前配置好
        maven 'maven3.6.0'
    }

    stages {
        stage('GitPull') {
            steps {
                // 从Gitee的master分支获取代码
                git url: "https://gitee.com/lakernote/easy-admin.git", branch: "master"
            }
        }

        stage('Build') {
            steps {
                // 运行Maven 跳过单元测试
                sh 'mvn clean package -Dmaven.test.skip=true'
            }
        }

        stage('Archive') {
            steps {
                // 会把target/*.jar文件归档到Jenkins的工作目录中的 archive 目录下
                archiveArtifacts 'target/*.jar'
            }
        }

        stage('Deploy') {
            steps {
                sh """
                    echo '开始部署'
                    pwd
                    date
                   """
            }
        }
    }

    post {
        // 构建后的操作
        success {
            sh "echo '构建成功'"
        }
        failure {
            sh "echo '构建失败'"
        }
        always {
            sh "echo '[构建完成]'"
        }
    }
}