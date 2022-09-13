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
        dir('api-test') {    
          git credentialsId: 'git', url: 'git@github.com:fraanpsilva/api-test-RestaAssured.git'
          sh 'mvn test'
        }            
      }

    }

    stage('Deploy Frontend'){
      steps{
        dir('frontend') {
          git credentialsId: 'git', url: 'git@github.com:fraanpsilva/tasks-frontend.git'
          sh 'mvn clean package'
          deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8080/')], contextPath: 'tasks', war: 'target/tasks.war'
        }
      }

    }
    
  }
}
