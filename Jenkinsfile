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

         stage('API Tests'){
            steps{
                git credentialsId: 'git', url: 'git@github.com:fraanpsilva/api-test-RestaAssured.git'
                sh 'mvn test'
                
            }

        }
    }

}
