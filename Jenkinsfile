pipeline {
    agent any
    stages {
        stage('Build Backend'){
            steps{
                sh 'mvn clean package -DskipTests=true'
            }
        }

        stage('Unit Tests'){
            steps{
                sh 'mvn test'
            }
        }

// config Sonarqube
        stage('Sonar Analysis'){
            environment {
                scannerHome = tool 'SONAR_SCANNER'
            }
            steps{
                withSonarQubeEnv('SONAR_LOCAL'){
                    sh "${scannerHomer}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.login=sqp_5973eb7788fffbba0060355cbb3c7d77a57bd5b0 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/mvn/**,**/src/test/**,**/model/**,**Application.java"
                }
            }
        }
    }
}
