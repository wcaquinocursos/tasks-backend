pipeline {
    agent  any
    stages {
        stage ('Build Backend'){
            steps {
                bat 'mvn clean package -DskipTest=true'
            }
        }
        /*stage ('Meio'){
            steps {
                bat 'echo meio'
                bat 'echo meio parte 2'
            }
        }
        stage ('Fim'){
            steps {
                sleep (5)
                bat 'echo Fim'
            }
        }*/
    }
}
