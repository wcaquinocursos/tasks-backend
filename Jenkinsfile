pipeline {
    agent any
    stages{
        stage ('Build Backend'){
            steps {
                bat 'mvn clean package'
            }
        }
        stage ('Aprovação para Deploy')
            steps {
                input ('Deseja prosseguir?')
            }
        }
        stage ('Deploy Backend'){
            steps {
                deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
            }
        }
        stage ('Deploy Frontend'){
            steps {
                dir('frontend') {
                    git credentialsId: 'github_login', url: 'https://github.com/zeadailson/tasks-frontend'
                    bat 'mvn clean package'
                    deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks', war: 'target/tasks.war'
                }
            }
        }
    }
}