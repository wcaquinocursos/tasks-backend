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
			environment {
				scannerHome = tool 'Sonar_Scanner'
			}
			steps {
				withSonarQubeEnv('Sonnar_Local') {
					bat "${scannerHome}\\bin\\sonar-scanner -Dsonar.projectKey=deploy-backend -Dsonar.host.url=http://localhost:9000 -Dsonar.login=sqa_1718217ed5b2bd5645bc32714417c2ae70f21983 -Dsonar.java.binaries=target -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**Application.java -Dsonar.qualitygate.wait=true"
				}
			}
		}
		stage ('Sonarqube Quality Gate') {
			steps {
				sleep(10)
				timeout(time: 1, unit: 'MINUTES') {
					waitForQualityGate abortPipeline: true
				}
			}
		}
	}
}