pipeline {
    agent any
    stages {
        stage ('Build Backend') {
            steps {
                bat 'mvn clean package -DskipTests=true'
            }
        }
		stage ('Unit Tests') {
            steps {
                bat 'mvn test'
            }
        }
		stage ('Deploy Backend') {
            steps {
                deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
            }
        }
		stage ('API Tests') {
            steps {
				dir('api-tests'){
					git credentialsId: 'GitHubLogin', url: 'https://github.com/elyfranmedeiros/tasks-api-test'
					bat 'mvn test'
				}
            }
		}	
		stage ('Deploy Frontend') {
            steps {
				dir('frontend') {
					git credentialsId: 'GitHubLogin', url: 'https://github.com/elyfranmedeiros/tasks-frontend'
					bat 'mvn clean package'
					deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks', war: 'target/tasks.war'
				}	
            }
		}
		stage ('Functional Tests') {
            steps {
				dir('functional-tests'){
					git credentialsId: 'GitHubLogin', url: 'https://github.com/elyfranmedeiros/tasks-functional-tests'
					bat 'mvn test'
				}
            }
		}
    }
}