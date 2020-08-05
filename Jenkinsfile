pipeline {
  agent any
  stages {
    stage('build') {
      sh 'mvn clean package -DskipTests=true'
    }
  }
}