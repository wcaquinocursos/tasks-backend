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
                dir('api-test') {
                    git credentialsId: 'ecc10728-9334-48e3-b2d9-6aac0585aab1', url: 'https://github.com/rodrigovaloski/tasks-apitest.git'
                    sh 'mvn test'
                }
            }
        }
        stage('Deploy Frontend'){
            steps {
                dir('tasks-frontend') {
                    git credentialsId: 'ecc10728-9334-48e3-b2d9-6aac0585aab1', url: 'https://github.com/rodrigovaloski/tasks-frontend.git'
                    sh 'mvn clean package -DskipTests=true'
                    deploy adapters: 
                    [tomcat9(credentialsId: 'tomcat_admin', path: '', url: 'http://localhost:8080/')], contextPath: 'tasks', war: 'target/tasks.war'
                }
            }
        }  
        stage('Functional Test'){            
            steps {
                sleep(10)
                dir('functional-test') {
                    git credentialsId: 'ecc10728-9334-48e3-b2d9-6aac0585aab1', url: 'https://github.com/rodrigovaloski/tasks-functionaltest.git'
                    sh 'mvn test'
                }
            }
        }     
        stage('Deploy Prod'){
            steps {
              sh "docker-compose build"
              sh "docker-compose up -d"
            }
        }
        stage('Health Check'){        
            steps {
            	sleep(5)
            	dir('functional-test') {              
              		sh "mvn verify -Dskip.surefire.tests"
              	}
            }
        }
    }
}