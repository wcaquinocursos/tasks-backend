pipeline {
	agent any
	stages {
		stage ('Build Backend') {
			steps {
				bat 'mvn clean package'
			}
		}
		stage ('Deploy Frontend') {
			steps {
				dir('frontend') {
					git credentialsId: '', url: 'https://github.com/joseneto111/tasks-frontend'
					bat 'mvn clean packege'
					deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path:'', url: 'http://20.231.82.89:8888/')], contextPath: 'tasks', war: 'target/tasks.war'
				}
			}
		}
	}
}
