pipeline {
	agent any
    parameters {
        choice(
            choices: ['Não', 'Sim'],
            description: 'Deseja implantar no ambiente de produção?',
            name: 'IMPLANTAR_PRODUCAO'
        )
        choice(
            choices: ['Não', 'Sim'],
            description: 'Tem certeza que deseja implantar em produção?',
            name: 'CONFIRMAR_IMPLANTACAO'
    )
    }

    stages {
        stage ('Build Backend') {
            steps {
                bat 'echo Build Backend'
                bat 'mvn clean package -DskipTests=true' //-DskipTests=true ele não vai executar os test unit, eu deixei separado
            }
        }
        stage ('Unit Tests') {
            steps {
                bat 'echo Realizando testes unitários'
                bat 'mvn test'
            }
        }
        stage ('Sonar Analysis') {
            environment { // Variável que criado no jenkins
                scannerHome = tool 'SONAR_SCANNER'
            }
            steps {
                bat 'echo Realizando análise dos testes unitários'
                withSonarQubeEnv('SONAR_LOCAL') { // variável de ambiente do jenkins
                    //PC MASTER RACER
                    bat "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.login=80c5436f8b8b3ca9d1c53e917dbc499b0a93e310 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**Application.java"
                    
                    //Note
                    //bat "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.login=cacf3d1f1d8b850884acbafab1166216c29f7ce5 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**Application.java"
		     sleep(10)
                } // está dizendo todo o caminho do scannerhome onde jenkins instalou automático para mim.
            }
        }
        stage ('Quality Gate') {
            steps {
                bat 'echo Validando a porcentam da cobertura do código no SonarQube'
                    sleep(1)
                    timeout(time: 1, unit: 'MINUTES') {
                    //   bat 'echo Quality Gate is OK'
		                waitForQualityGate abortPipeline: true // vai esperar uma msg do webhook do sonar. se demorar muito ele vai da timeout
                }
            }
        }
        stage ('Deploy Backend') {
            steps {
                bat 'echo Realizando Deploy Backend'
                deploy adapters: [tomcat8(credentialsId: 'tomcat_login', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
            }
        }
        stage ('API Test') {
            steps {
                bat 'echo Realizando testes unitários'
                dir('api-test') { // cria um diretório para melhor organização
                    git credentialsId: 'github_login', url: 'https://github.com/orlando-dev/tasks-api-test'
                    bat 'mvn test'
                }
            }
        }
        stage ('Deploy Frontend') {
            steps {
                bat 'echo Realizando deploy Frontend'
                dir ('frontend') {// criando uma pasta para organização
                    git credentialsId: 'github_login', url: 'https://github.com/orlando-dev/tasks-frontend'  //baixando o cod
                    bat 'mvn clean package' // realizando o build e depois o passo abaixo, faz o deploy
                    deploy adapters: [tomcat8(credentialsId: 'tomcat_login', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks', war: 'target/tasks.war'
                }   
            }
        }
        stage ('Funcional Test') {
            steps {
                bat 'echo Realizando testes Funcionais E2E'
                dir ('funcional-test') {
                    git credentialsId: 'github_login', url: 'https://github.com/orlando-dev/tasks-funcional-tests'
                    bat 'mvn test'
                }
            }
        }



        stage ('Deploy Prod') {
            when {
                expression { params.IMPLANTAR_PRODUCAO == 'Sim' && params.CONFIRMAR_IMPLANTACAO == 'Sim'}
            }
            steps {
                bat 'echo Criando build de produção'
                bat 'docker-compose build'
                
                bat 'echo Subindo aplicação de produção'
                bat 'docker-compose up -d'
            }
        }
        stage ('Health Check') {
            when {
                expression { params.IMPLANTAR_PRODUCAO == 'Sim' && params.CONFIRMAR_IMPLANTACAO == 'Sim'}
            }
            steps {
                sleep(20)
                bat 'echo Verificando se aplicação está no Ar'
                dir ('funcional-test') {
                    bat 'mvn verify -Dskip.surefire.tests'
                }
            }
        }
    }
    post {
        always {
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml, api-test/target/surefire-reports/*.xml, funcional-test/target/surefire-reports/*.xml, funcional-test/target/failsafe-reports/*.xml'
            archiveArtifacts artifacts: 'target/tasks-backend.war, frontend/target/tasks.war', followSymlinks: false, onlyIfSuccessful: true
        }
        unsuccessful {
            emailext attachLog: true, body: 'See the attached log below', subject: 'Version $APP_VERSION has failed', to: 'orlandox0796+jenkins@gmail.com'
        }
        fixed {
            emailext attachLog: true, body: 'Ok', subject: 'Version $APP_VERSION is Ok', to: 'orlandox0796+jenkins@gmail.com'
        }
    }
}
