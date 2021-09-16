pipeline {
    agent any
    stages{
        stage('Build backend'){
            steps {
                sh 'mvn clean package -DskipTests=true'
            }
        }
    }
}