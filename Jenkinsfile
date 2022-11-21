pipeline {
    agent any

    tools {
        maven '3.8.6'
    }

    stages {
        stage ('Build Backend') {
            steps {
                sh 'mvn clean package -DskipTests=true'
            }
        }

        stage ('Run Unit Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage ('Run SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SONAR_LOCAL') {
                    sh 'mvn ${env.SONAR_MAVEN_GOAL}'
                }
            }
        }
    }
}