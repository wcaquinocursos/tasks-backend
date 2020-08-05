pipeline {
  agent any
  stages {
    stage('Build backend') {
      steps {
        sh 'mvn clean package -DskipTests=true'
      }
    }
    stage('Unit Tests') {
      steps {
        sh 'mvn test'
      }
    }
    stage('Sonar Analysis') {
      environment {
        scannerHome = tool 'SONAR_SCANNER'
      }
      steps {
        withSonarQubeEnv('SONAR') {
          sh "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.login=31442faad94436bfc03792568484e5c9e89feeff -Dsonar.java.binaries=target"
        }
      }
    }
    stage('Quality Gate') {
      steps {
        sleep(5)
        timeout(time: 1, unit: 'MINUTES') {
          waitForQualityGate abortPipeline: true
        }
      }
    }
    stage('Deploy Backend') {
      steps {
        deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
      }
    }
    stage('API Tests') {
      steps {
        dir('api test') {
          git credentialsId: 'github-viniciusflores', url: 'https://github.com/viniciusflores/tasks-api-test.git'
          sh 'mvn test'
        }
      }
    }
    stage('Deploy Front-end'){
      steps {
        dir('frontend') {
          git credentialsId: 'github-viniciusflores', url: 'https://github.com/viniciusflores/tasks-frontend.git'
          sh 'mvn clean package -DskipTests=true'
          deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks', war: 'target/tasks.war'
        }
      }
    }
  }
}
