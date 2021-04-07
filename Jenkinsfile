pipeline {
    agent  any
    stages {
        stage ('Build Backend'){
            steps {
                bat 'mvn clean package -DskipTest=true'
            }
        }
        stage ('Unit Test'){
            steps {
                bat 'mvn test'
            }
        }
        stage ('Sonar Analysis'){
            environment{
                scannerHome = tool 'SONAR_SCANNER'    
            }
            steps {
                withSonarQubeEnv('SONAR_LOCAL'){
                    bat "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.login=0f445d2a90d58958a91fdbe10852696ba2ba2a0b -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**Application.java"
                }
            }
        }
        /*stage ('Quality Gate'){
            steps {
                sleep(20)
                timeout(time: 1, unit: 'MINUTES'){
                    waitForQualityGate abortPipeline: true
                }
            }
        }*/
        stage ('Deploy Backend'){
            steps {
                deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8001')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
            }
        }
        stage ('API Test'){
            steps {
                dir('api-test') {
                    git branch: 'main', credentialsId: 'github_login', url: 'https://github.com/rodrigofa1305/tasks-api-test'
                    bat 'mvn test'
                }
            }
        }
        stage ('Deploy Frontend'){
            steps {
                dir('frontend') {
                    git branch: 'master', credentialsId: 'github_login', url: 'https://github.com/rodrigofa1305/tasks-frontend'
                    bat 'mvn clean package'
                    deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8001')], contextPath: 'tasks', war: 'target/tasks.war'
                }
            }
        }
    }
}
