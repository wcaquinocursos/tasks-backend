pipeline {
    agent  any
    stages {
        stage ('Build Backend'){
            steps {
                bat 'mvn clean package -DskipTest=true'
            }
        }
        stage ('Unit Test'){
            steps {
                bat 'mvn test'
            }
        }
        stage ('Sonar Analysis'){
            environment{
                scannerHome = tool 'SONAR_SCANNER'    
            }
            steps {
                withSonarQubeEnv('SONAR_LOCAL'){
                    bat "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.login=0f445d2a90d58958a91fdbe10852696ba2ba2a0b -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**Application.java"
                }
            }
        }
        /*stage ('Quality Gate'){
            steps {
                sleep(30)
                timeout(time: 1, unit: 'MINUTES'){
                    waitForQualityGate abortPipeline: true
                }
            }
        }*/
        stage ('Deploy Backend'){
            steps {
                deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8001')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
            }
        }
        stage ('API Test'){
            steps {
                dir('api-test') {
                    git branch: 'main', credentialsId: 'github_login', url: 'https://github.com/rodrigofa1305/tasks-api-test'
                    bat 'mvn test'
                }
            }
        }
        stage ('Deploy Frontend'){
            steps {
                dir('frontend') {
                    git branch: 'master', credentialsId: 'github_login', url: 'https://github.com/rodrigofa1305/tasks-frontend'
                    bat 'mvn clean package'
                    deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8001')], contextPath: 'tasks', war: 'target/tasks.war'
                }
            }
        }
        stage ('Funcional Test'){
            steps {
                dir('funcional-test') {
                    git branch: 'main', credentialsId: 'github_login', url: 'https://github.com/rodrigofa1305/tasks-funcional-tests'
                    bat 'mvn test'
                }
            }
        }
        stage ('Deploy Producao'){
            steps {
                dir('funcional-test') {
                    bat 'docker-compose build'
                    bat 'docker-compose up -d'
                }
            }
        }
        stage ('HealthCheck'){
            steps {
                sleep(270)
                dir('funcional-test') {
                   bat 'mvn verify -Dskip.surefire.tests'
                }
            }
        }
    }
    post{
        always{
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml, api-test/target/surefire-reports/*.xml, funcional-test/target/surefire-reports/*.xml, funcional-test/target/failsafe-reports/*.xml'
            archiveArtifacts artifacts: 'target/tasks-backend.war, frontend/target/tasks.war', onlyIfSuccessful: true
        }
        unsuccessful{
            emailext attachLog: true, body: 'See the attached log below', subject: 'Build $BUILD_NUMBER has failed', to: 'rodrigofa1305+jenkins@gmail.com'
        }
        fixed{
            emailext attachLog: true, body: 'See the attached log below', subject: 'Build is Fine!', to: 'rodrigofa1305+jenkins@gmail.com'
        }
    }
}
