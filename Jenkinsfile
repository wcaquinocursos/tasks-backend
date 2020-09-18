pipeline {
    agent any
    stages {
        stage ('Build Backend') {
            steps {
                bat 'mvn clean package -DskipTests=true'
            }
        }
        stage ('Unit Test') {
            steps {
                bat 'mvn test'
            }
        }
        stage ('Sonar analysis') {
            environment {
                scannerHome = tool 'SONAR_SCANNER'
            }
            steps {
                withSonarQubeEnv('SONAR_LOCAL') {
                    bat "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeploBack -Dsonar.host.url=http://127.0.0.1:9000 -Dsonar.login=ee946d1fb6cbc1a9cf240c8114506cbff3a08a41 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**Applicantion.java"
                }
            }
        }
        stage ('Quality Gate') {
            options {
            timeout(time: 5, unit: 'MINUTES')
            retry(2)
            }
            steps {
                sleep(10){
                    waitForQualityGate(abortPipeline: true)
                }
            }
        }
        stage ('Deploy Backend') {
            steps {
                deploy adapters: [tomcat8(credentialsId: 'TomcatLogin2', path: '', url: 'http://127.0.0.1:8001/')], contextPath: 'tasks-backend', war: 'target\\tasks-backend.war'
            }
        }
        stage ('API Test') {
            steps {
                dir('api-test') {
                    git credentialsId: 'github_login', url: 'https://github.com/gugafer/tasks-api-test'
                    bat 'mvn test'
                }
            }
        }
        stage ('Deply Frontend') {
            steps {
                dir('frontend') {
                    git credentialsId: 'github_login', url: 'https://github.com/gugafer/tasks-frontend'
                    bat 'mvn clean package' 
                    deploy adapters: [tomcat8(credentialsId: 'TomcatLogin2', path: '', url: 'http://127.0.0.1:8001/')], contextPath: 'tasks', war: 'target\\tasks.war'
       
                }
            }
        }
        stage ('Functional Test') {
            steps {
                dir('functional-test') {
                    git credentialsId: 'github_login', url: 'https://github.com/gugafer/tasks-functional-test'
                    bat 'mvn test'
                }
            }
        }
        stage ('Deploy Prod') {
            steps {
                bat 'docker-compose build'
                bat 'docker-compose up -d'
            }
        }       
    }
}


