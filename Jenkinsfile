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
        /*stage ('Fim'){
            steps {
                sleep (5)
                bat 'echo Fim'
            }
        }*/
    }
}
