pipeline {
    agent any
    stages {
        stage ('Build BackEnd') {
            steps {
                bat 'mvn clean package -DskipTests = true'
            }
        }
    }
}