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
        stage ('Quality Gate'){
            steps {
                timeout(time: 1, unit: 'MINUTES'){
                waitForQualityGate abortPipeline: true
                }
            }
        }
    }
}