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
            environment {
                SONAR_SCANNER = tool name: 'SONAR_SCANNER', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
            }

            steps {
                withSonarQubeEnv('SONAR_LOCAL') {
                    sh "mvn ${env.SONAR_MAVEN_GOAL} -Dsonar.login=${env.SONAR_AUTH_TOKEN}"
                }
            }
        }
        stage('Wait for Quality Gate') {
            timeout(time: 2, unit: 'MINUTES') {
                waitForQualityGate abortPipeline: true
            }
        }
    }
}