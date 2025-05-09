pipeline {
    agent any

    stages {
        stage('Clonar código') {
            steps {
                echo 'Código clonado desde GitHub'
            }
        }

        stage('Build backend') {
            steps {
                dir('backend') {
                    sh 'docker build -t grandt-backend .'
                }
            }
        }

        stage('Build frontend') {
            steps {
                dir('frontend') {
                    sh 'docker build -t grandt-frontend .'
                }
            }
        }

        stage('Levantar servicios') {
            steps {
                sh 'docker-compose up -d'
            }
        }
    }
}
