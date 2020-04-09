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
        //stage('Quality Gate'){
        //     steps{
        //         sleep(20)
        //         timeout(time:1,unit:'MINUTES'){
        //            waitForQualityGate abortPipeline: true
        //         
        //     }
        //}
        stage('Deploy Backend'){
            steps{
                deploy adapters: [tomcat8(credentialsId: 'TomcatAdmin', path: '', url: 'http://localhost:8001')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
            }
        }  
    }
}

deploy adapters: [tomcat8(credentialsId: 'TomcatAdmin', path: '', url: 'http://localhost:8001')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'


