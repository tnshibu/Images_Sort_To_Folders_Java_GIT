pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Jenkins Pipeline - Building..'
                sh "mvn clean install"
            }
        }
        stage('Test') {
            steps {
                echo 'Jenkins Pipeline - Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Jenkins Pipeline - Deploying....'
            }
        }
    }
}
