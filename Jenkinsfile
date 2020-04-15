pipeline{
    agent any
    stages{
        stage('Build Backend'){
            steps{
                bat 'mvn clean package -DskipTests=true'
            }
        }
        stage('Unit Tests'){
            steps{
                bat 'mvn test'
            }
        }    
        //stage('Sonar Analysis'){
        //    environment{
        //        scannerHome = tool 'SONAR_SCANNER'
        //    }
        //    steps{
        //        withSonarQubeEnv('SONAR_LOCAL')

        //        bat "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.login=cf6826d57f1e453e08ecbd6cf862472061f66 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**Application.java"
        //    }
        //} 
        //stage('Quality Gate'){
        //     steps{
        //         sleep(20)
        //         timeout(time:1,unit:'MINUTES'){
        //            waitForQualityGate abortPipeline: true
        //         
        //     }
        //}
        stage('Deploy Backend'){
            steps{
                deploy adapters: [tomcat8(credentialsId: 'TomcatAdmin', path: '', url: 'http://localhost:8001')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
            }
        }
        stage('API Test'){
            steps{
                dir('api-test'){
                    git credentialsId: '23956211-31cd-44a1-80c5-5d627a0f1d03', url: 'https://github.com/HenriqueGalli/tasks-api-test'
                    bat 'mvn test'
                }                
            }
        }
        stage('Deploy Frontend'){
            steps{
                dir('frontend'){
                    git credentialsId: '23956211-31cd-44a1-80c5-5d627a0f1d03', url: 'https://github.com/HenriqueGalli/tasks-frontend'
                    bat 'mvn clean package'
                    deploy adapters: [tomcat8(credentialsId: 'TomcatAdmin', path: '', url: 'http://localhost:8001')], contextPath: 'tasks', war: 'target/tasks.war'                   
                }
                
            }
        }
        stage('Functional Tests'){
            steps{
                dir('functional-test'){
                    git credentialsId: '23956211-31cd-44a1-80c5-5d627a0f1d03', url: 'https://github.com/HenriqueGalli/functional-test'
                    bat 'mvn test'
                }
            }
        }
        stage('Deploy prod'){
            steps{
                bat 'docker-compose build'
                bat 'docker-compose up -d'
            }
        }
        stage('Health Check'){
            steps{
                sleep(10)
                dir('functional-test'){                    
                    bat 'mvn verify -Dskip.surefire.tests'
                }
            }
        }
    }
    post{
        always{
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml,api-test/target/surefire-reports/*.xml,functional-test/target/surefire-reports/*.xml,functional-test/target/failsafe-reports/*.xml'
            archiveArtifacts artifacts: 'target/tasks-backend.war, frontend/target/tasks.war', onlyIfSuccessful:true
        }
        unsuccessful{
            emailext attachLog: true, body: 'See the attached log', subject: 'Build Failed', to: 'henrique.galli@atomicsolutions.com.br'
        }
        fixed{
            emailext attachLog: true, body: 'See the attached log below', subject: 'Build is fixed', to: 'henrique.galli@atomicsolutions.com.br'
        }
    }
}

