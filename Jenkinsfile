pipeline {
	agent any
	stages {
		stage ('Backend Build') {
			steps {
				bat 'mvn clean package -DskipTests=true'
			}
		}
		stage ('Backend Unit Tests') {
			steps {
				bat 'mvn test'
			}
		}
		stage ('Sonarqube Scanner Analysis') {
			steps {
				withSonarQubeEnv('Sonar_Local') {
					bat "mvn package sonar:sonar -Dsonar.projectKey=deploy-backend -Dsonar.host.url=http://localhost:9000 -Dsonar.java.binaries=target -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**Application.java"
				}
			}
		}
		stage ('Sonarqube Quality Gate') {
			steps {
				sleep(5)
				timeout(time: 1, unit: 'MINUTES') {
					waitForQualityGate abortPipeline: true
				}
			}
		}
		stage ('Backend Deploy') {
			steps {
				deploy adapters: [tomcat8(credentialsId: 'tomacat_admin', path: '', url: 'http://localhost:8901/')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
			}
		}
	}
}