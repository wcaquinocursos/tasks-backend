pipeline{
    agent any
    stages{
        stage('Build Backend'){
            steps{
                bat 'mvn clean package -DskipTests=true'
            }
        }
        stage('Unit Tests'){
            steps{
                bat 'mvn test'
            }
        }    
        //stage('Sonar Analysis'){
        //    environment{
        //        scannerHome = tool 'SONAR_SCANNER'
        //    }
        //    steps{
        //        withSonarQubeEnv('SONAR_LOCAL')

        //        bat "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.login=cf6826d57f1e453e08ecbd6cf862472061f66 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**Application.java"
        //    }
        //}   
    }
}



