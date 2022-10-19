pipeline {
	agent any
    stages {
        stage ('Build Backend') {
            steps {
                bat 'mvn clean package -DskipTests=true' //-DskipTests=true ele não vai executar os test unit, eu deixei separado
            }
        }
        stage ('Unit Tests') {
            steps {
                bat 'mvn test'
            }
        }
        stage ('Sonar Analysis') {
            environment { // Variável que criado no jenkins
                scannerHome = tool 'SONAR_SCANNER'
            }
            steps {
                withSonarQubeEnv('SONAR_LOCAL') { // variável de ambiente do jenkins
                    bat "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.login=cacf3d1f1d8b850884acbafab1166216c29f7ce5 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**Application.java"
                } // está dizendo todo o caminho do scannerhome onde jenkins instalou automático para mim.
            }
        }
        stage ('Quality Gate') {
            steps {
                //sleep(300)
                //timeout(time: 4, unit: 'MINUTES') {
                    //waitForQualityGate abortPipeline: true // vai esperar uma msg do webhook do sonar. se demorar muito ele vai da timeout
                //}
            }
        }
        stage ('Deploy Backend') {
            steps {
                //máquina home
                //deploy adapters: [tomcat8(credentialsId: 'tomcatLogin', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
                
                //máquina note
                deploy adapters: [tomcat8(credentialsId: 'tomcat_login', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
            }
        }
        stage ('API Test') {
            steps {
                dir('api-test') { // cria um diretório para melhr organização
                    //máquina home
                    //git credentialsId: 'github_login_correct', url: 'https://github.com/orlando-dev/tasks-api-test'
                    
                    //máquina note
                    git credentialsId: 'github_login', url: 'https://github.com/orlando-dev/tasks-api-test'
                    bat 'mvn test'
                }
            }
        }
        stage ('Deploy Frontend') {
            steps {
                dir ('frontend') {// criando uma pasta para organização
                    //Máquina home
                    //git credentialsId: 'github_login_correct', url: 'https://github.com/orlando-dev/tasks-frontend'
                    //bat 'mvn clean package'
                    //deploy adapters: [tomcat8(credentialsId: 'TomcatLogin', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks', war: 'target/tasks.war'

                    //Máquina note
                    git credentialsId: 'github_login', url: 'https://github.com/orlando-dev/tasks-frontend'  //baixando o cod
                    bat 'mvn clean package' // realizando o build e depois o passo abaixo, faz o deploy
                    deploy adapters: [tomcat8(credentialsId: 'tomcat_login', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks', war: 'target/tasks.war'
                }   
            }
        }
        stage ('Funcional Test') {
            steps {
                dir ('funcional-test') {
                    //Máquina home
                    //git credentialsId: 'github_login_correct', url: 'https://github.com/orlando-dev/tasks-funcional-tests'
                    
                    //Máquina note
                    git credentialsId: 'github_login', url: 'https://github.com/orlando-dev/tasks-funcional-tests'
                    bat 'mvn test'
                }
            }
        }
        stage ('Deploy Prod') {
            steps {
                bat 'docker-compose build'
                bat 'docker-compose up -d'
            }
        }
        // stage ('Health Check') {
        //     steps {
        //         sleep(10)
        //         dir ('funcional-test') {
        //             bat 'mvn verify -Dskip.surefire.tests'
        //         }
        //     }
        // }
    }
}
