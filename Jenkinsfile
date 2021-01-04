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
         stage('Sonar Analysis'){
            environment{
                scannerHome = tool 'SONAR_SCANNER'
            }
            steps{
                withSonarQubeEnv('SONAR_LOCAL'){
                    bat "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.login=775d6e88db77e17f2b44c4656646b9de28ad94de -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn**,**/src/test**,**/model/**,**Application.java "
               }
            }
        }
        stage ('Quality Gate'){
            steps {
                sleep(30) 
                timeout(time:1, unit:'MINUTES'){
                    waitForQualityGate abortPipeline: true
                }
                
            }
        }

    }  
} 