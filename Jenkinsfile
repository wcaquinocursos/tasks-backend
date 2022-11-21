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
                script {
                    def scannerHome = tool 'SONAR_SCANNER'
                }

                withSonarQubeEnv('SONAR_LOCAL') {
                    sh '${scannerHome}/bin/sonar-scanner'
                    sh 'mvn ${env.SONAR_MAVEN_GOAL}'
                }
            }
        }
    }
}