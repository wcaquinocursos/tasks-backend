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
    }
}