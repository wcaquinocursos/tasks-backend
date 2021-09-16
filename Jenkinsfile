pipeline {
    agent any
    stages{
        stage('Build backend'){
            steps {
                sh 'mvn clean package -DskipTests=true'
            }
        }
        stage('Unit tests'){
            steps {
                sh 'mvn test'
            }
        }
        stage('Sonar Analysis'){
            environment{
                scannerHome = tool 'SONAR_SCANNER'
            }
            steps {
                withSonarQubeEnv('SONAR_LOCAL'){
                    sh " ${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://34.125.175.57:9000 -Dsonar.login=32ba190cfd18acce92461e08d087e6c1618dfa80 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**Application.java"
                }
            }
        }
        stage('Quality Gate'){
            steps {
                sleep(10)
                timeout(time: 1, unit:'MINUTES'){
                    waitForQualityGate abortPipeline: true
                }                
            }
        }
        stage('Deploy Backend'){
            steps{
                deploy adapters: 
                [tomcat9(credentialsId: 'tomcat_admin', path: '', url: 'http://localhost:8080/')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
            }
        }
        stage('API Test'){
            steps {
                git credentialsId: 'ecc10728-9334-48e3-b2d9-6aac0585aab1', url: 'https://github.com/rodrigovaloski/tasks-apitest.git'
                sh 'mvn test'
            }
        }
    }
}