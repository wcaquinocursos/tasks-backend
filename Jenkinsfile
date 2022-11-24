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
          sh "mvn clean package"
          deploy adapters: [tomcat9(credentialsId: 'tasks-app-tomcat', path: '', url: 'http://tasks-app:8001/')], contextPath: 'tasks', war: 'target/tasks.war'
        }
      }
    }

    stage('Functional Test') {
      steps {
        dir('functional-test') {
          git 'https://github.com/marcelsby/tasks-functional-test'
          sh 'mvn test -Dapp.selenium.hub.url=http://selenium-hub:4444 -Dapp.baseurl=http://tasks-app:8001/tasks'
        }
      }
    }
  }

  post {
    always {
      junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml, api-test/target/surefire-reports/*.xml, functional-test/target/surefire-reports/*.xml'
    }

    unsuccessful {
      emailext attachLog: true, body: 'See the attached log below.', subject: 'Build $BUILD_NUMBER has failed', to: 'marcel.sby.br@gmail.com'
    }

    fixed {
      emailext attachLog: true, body: 'See the attached log below.', subject: 'Build $BUILD_NUMBER is fine', to: 'marcel.sby.br@gmail.com'
    }
  }
}