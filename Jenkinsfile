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

        stage('Deploy Backend'){
            steps{
                deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8080/')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
            }

        }

// config Sonarqube
        //stage('Sonar Analysis'){
            //environment {
          //      scannerHome = tool 'SONAR_SCANNER'
            //}
            //steps{
                //withSonarQubeEnv('SONAR_LOCAL'){
                    //sh "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.login=sqp_5973eb7788fffbba0060355cbb3c7d77a57bd5b0 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/mvn/**,**/src/test/**,**/model/**,**Application.java"
                //}
            //}
        //}
    //}

        //stage('Quality Gate'){
            //steps{
                //sleep(5)
                //timeout(time: 1, unit: 'MINUTES'){
                    //waitForQualityGate abortPipeline: true
               // }
                
            //}
       // }
}
