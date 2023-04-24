pipeline {
	agent any
    // parameters {
    //     choice(
    //         choices: ['Não', 'Sim'],
    //         description: 'Deseja implantar no ambiente de produção?',
    //         name: 'IMPLANTAR_PRODUCAO'
    //     )
    //     choice(
    //         choices: ['Não', 'Sim'],
    //         description: 'Tem certeza que deseja implantar em produção?',
    //         name: 'CONFIRMAR_IMPLANTACAO'
    // )
    // }

    stages {
        stage ('Build Backend') {
            steps {
                sh 'echo Build Backend'
                sh 'mvn clean package -DskipTests=true'
            }
        }
        stage ('Unit Tests') {
            steps {
                sh 'echo Realizando testes unitários'
                sh 'mvn test'
            }
        }
        stage ('Deploy Backend') {
            steps {
                sh 'echo Realizando Deploy Backend'
                sshagent(['ssh-server-jenkins']) {
                    sh "ssh -o StrictHostKeyChecking=true sapo@${INTRANET_DESENV} cd"
                    sh "pwd"
                    sh "ip a"
                    sh "echo 'hello world' > myfile.txt"
                    sh "mv myfile.txt sapo@${INTRANET_DESENV}:/opt/tomcat/webapps"
                    sh "ip a"
                    sh "ssh ip a"
                    sh "ssh sapo@${INTRANET_DESENV} ip a"
                    sh "sapo@${INTRANET_DESENV} ip a"
                }
            }
        }
    //     stage ('API Test') {
    //         steps {
    //             sh 'echo Realizando testes unitários'
    //             dir('api-test') {
    //                 git credentialsId: 'github_login', url: 'https://github.com/orlando-dev/tasks-api-test'
    //                 sh 'mvn test'
    //             }
    //         }
    //     }
    //     stage ('Deploy Frontend') {
    //         steps {
    //             sh 'echo Realizando deploy Frontend'
    //             dir ('frontend') {// criando uma pasta para organização
    //                 git credentialsId: 'github_login', url: 'https://github.com/orlando-dev/tasks-frontend'  //baixando o cod
    //                 sh 'mvn clean package' // realizando o build e depois o passo abaixo, faz o deploy
    //                 deploy adapters: [tomcat8(credentialsId: 'tomcat_login', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks', war: 'target/tasks.war'
    //             }   
    //         }
    //     }
    //     stage ('Funcional Test') {
    //         steps {
    //             sh 'echo Realizando testes Funcionais E2E'
    //             dir ('funcional-test') {
    //                 git credentialsId: 'github_login', url: 'https://github.com/orlando-dev/tasks-funcional-tests'
    //                 sh 'mvn test'
    //             }
    //         }
    //     }
    //     stage ('Deploy Prod') {
    //         when {
    //             expression { params.IMPLANTAR_PRODUCAO == 'Sim' && params.CONFIRMAR_IMPLANTACAO == 'Sim'}
    //         }
    //         steps {
    //             sh 'echo Criando build de produção'
    //             sh 'docker-compose build'
                
    //             sh 'echo Subindo aplicação de produção'
    //             sh 'docker-compose up -d'
    //         }
    //     }
    //     stage ('Health Check') {
    //         when {
    //             expression { params.IMPLANTAR_PRODUCAO == 'Sim' && params.CONFIRMAR_IMPLANTACAO == 'Sim'}
    //         }
    //         steps {
    //             sleep(20)
    //             sh 'echo Verificando se aplicação está no Ar'
    //             dir ('funcional-test') {
    //                 sh 'mvn verify -Dskip.surefire.tests'
    //             }
    //         }
    //     }
    }
    post {
        always {
            // junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml, api-test/target/surefire-reports/*.xml, funcional-test/target/surefire-reports/*.xml, funcional-test/target/failsafe-reports/*.xml'
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