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
                dir('Backend') {
                    sh 'docker build -t backend .'
                }
            }
        }

        stage('Build frontend') {
            steps {
                dir('Frontend') {
                    sh 'docker build -t frontend .'
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
