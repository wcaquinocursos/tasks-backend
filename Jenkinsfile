pipeline{
    agent any
    stages{
        stage('Build Backend'){
            steps{
               bat 'mvn clean packge -DskipTests=true'
            }
        }
    }
}