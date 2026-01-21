pipeline {
  agent any
  stages {
    stage('Build Backend') {
      steps {
        bat 'mvn clean package -DskipTests=true'
      }
    }
    stage('Unit Tests') {
      steps {
        bat 'mvn clean verify'
      }
    }
    stage('SonarQube Analysis') {
      environment {
        SONARQUBE_SCANNER_HOME = tool 'SONAR_SCANNER'
      }
      steps {
        withSonarQubeEnv('SONAR_LOCAL') {
          bat """
            "${SONARQUBE_SCANNER_HOME}/bin/sonar-scanner.bat" ^
            -Dsonar.projectKey=DeployBack ^
            -Dsonar.host.url=http://localhost:9000 ^
            -Dsonar.login=76bd61c4ea9c4138e03a57e9d4be699ee5f2111f ^
            -Dsonar.java.binaries=target ^
            -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml ^
            -Dsonar.exclusions=**/.mvn/**,**/target/** ^
            -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**/*Application.java
          """
        }
      }
    }
    stage('Quality Gate') {
      steps {
        sleep(5)
        timeout(time: 1, unit: 'MINUTES') {
          waitForQualityGate abortPipeline: true
        }
      }
    }
    stage('Deploy Backend') {
      steps {
        deploy adapters: [tomcat8(alternativeDeploymentContext: '', credentialsId: 'TomcatLogin', path: '', url: 'http://127.0.0.1:8001/')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
      }
    }
    stage('API Tests') {
      steps {
        dir('api-test') {
          git branch: 'main', credentialsId: 'github_login', url: 'https://github.com/edRibeiro/tasks-api-test.git'
          bat 'mvn test'
        }
      }
    }
    stage('Deploy Frontend') {
      steps {
        dir('frontend') {
          git branch: 'master', credentialsId: 'github_login', url: 'https://github.com/edRibeiro/tasks-frontend.git'
          bat 'mvn clean package'
          deploy adapters: [tomcat8(alternativeDeploymentContext: '', credentialsId: 'TomcatLogin', path: '', url: 'http://127.0.0.1:8001/')], contextPath: 'tasks', war: 'target/tasks.war'
        }
      }
    }
    stage('Functional Test') {
      steps {
        dir('functional-test') {
          git branch: 'main', credentialsId: 'github_login', url: 'https://github.com/edRibeiro/tasks-functional-tests.git'
          bat 'mvn test'
        }
      }
    }
    stage('Deploy to Production') {
      steps {
        bat 'docker compose build'
        bat 'docker compose up -d'
      }
    }
    stage('Health Check') {
      steps {
        sleep(20)
        dir('functional-test') {
          bat 'mvn verify -Dskip.surefire.tests'
        }
      }
    }
  }
  post {
    always {
      junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml, api-test/target/surefire-reports/*.xml, functional-test/target/surefire-reports/*.xml, functional-test/target/failsafe-reports/*.xml'
      archiveArtifacts artifacts: 'target/tasks-backend.war, frontend/target/tasks.war', onlyIfSuccessful: true
    }
    unsuccessful {
      emailext attachLog: true, body: 'See the attached log below.', subject: 'Build $BUILD_NUMBER has failed', to: 'pipeline+jenkins@mail.com'
    }
    fixed {
        emailext attachLog: true, body: 'See the attached log below.', subject: 'Build is fine!!!', to: 'pipeline+jenkins@mail.com'
    }
  }
}
