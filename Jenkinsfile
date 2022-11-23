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
          sh "mvn ${env.SONAR_MAVEN_GOAL} -Dsonar.login=${env.SONAR_AUTH_TOKEN}"
        }
      }
    }

    stage('Wait for Quality Gate') {
      steps {
        timeout(time: 2, unit: 'MINUTES') {
          waitForQualityGate abortPipeline: true
        }
      }
    }

    stage('Deploy Backend') {
      steps {
        deploy adapters: [tomcat9(credentialsId: 'tasks-app-tomcat', path: '', url: 'http://tasks-app:8001/')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
      }
    }

    stage('API Test') {
      steps {
        dir('api-test') {
          git 'https://github.com/marcelsby/tasks-api-test'
          sh 'mvn test -Dapi.baseuri=http://tasks-app:8001/tasks-backend'
        }
      }
    }

    stage('Deploy Frontend') {
      steps {
        dir('frontend') {
          git 'https://github.com/marcelsby/tasks-frontend'
          sh 'mvn clean package'
          deploy adapters: [tomcat9(credentialsId: 'tasks-app-tomcat', path: '', url: 'http://tasks-app:8001/')], contextPath: 'tasks', war: 'target/tasks.war'
        }
      }
    }
  }
}