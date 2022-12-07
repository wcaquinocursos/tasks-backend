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
					bat "mvn package sonar:sonar -Dsonar.projectKey=deploy-backend -Dsonar.host.url=http://localhost:9000 -Dsonar.login=sqa_1718217ed5b2bd5645bc32714417c2ae70f21983 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**Application.java"
				}
			}
		}
		stage ('Sonarqube Quality Gate') {
			steps {
				sleep(10)
				timeout(time: 5, unit: 'MINUTES') {
					waitForQualityGate webhookSecretId: 'JenkinsSecretId', abortPipeline: true
				}
			}
		}
		stage ('Backend Deploy') {
			steps {
				deploy adapters: [tomcat8(credentialsId: 'tomacat_admin', path: '', url: 'http://localhost:8901/')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
			}
		}
		stage ('Backend API Test') {
			steps {
				dir('tasks-api-tests') {
					git credentialsId: 'github_login', url: 'https://github.com/C-Viana/tasks-api-tests.git'
					bat 'mvn clean test'
				}
			}
		}
		stage ('Frontend Deploy') {
			steps {
				dir('tasks-frontend') {
					git credentialsId: 'github_login', url: 'https://github.com/C-Viana/tasks-frontend.git'
					bat 'mvn clean package'
					deploy adapters: [tomcat8(credentialsId: 'tomacat_admin', path: '', url: 'http://localhost:8901/')], contextPath: 'tasks', war: 'target/tasks.war'
				}
			}
		}
		stage ('Frontend Functional Test') {
			steps {
				dir('tasks-front-tests') {
					git credentialsId: 'github_login', url: 'https://github.com/C-Viana/tasks-front-tests.git'
					bat 'mvn clean test'
				}
			}
		}
		stage ('Deploy Prod') {
			steps {
				bat 'docker-compose build'
				bat 'docker-compose up -d'
			}
		}
		stage ('Health Check') {
			steps {
				sleep(6)
				dir('tasks-front-tests'){
					bat 'mvn verify -Dskip.surefire.tests'
				}
			}
		}

	}
}
