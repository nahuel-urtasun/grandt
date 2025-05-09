pipeline {
    agent any

    environment {
        BACKEND_IMAGE = 'grandt-backend'
        FRONTEND_IMAGE = 'grandt-frontend'
    }

    stages {
        stage('Clonar Repo') {
            steps {
                git url: 'https://github.com/nahuel-urtasun/grandt.git', branch: 'master'
            }
        }

        stage('Construir Backend') {
            steps {
                dir('Backend') {
                    sh 'docker build -t ${BACKEND_IMAGE}:latest .'
                }
            }
        }

        stage('Construir Frontend') {
            steps {
                dir('Frontend') {
                    sh 'docker build -t ${FRONTEND_IMAGE}:latest .'
                }
            }
        }

        stage('Levantar Contenedores') {
            steps {
                script {
                    sh 'docker rm -f grandt-backend || true'
                    sh 'docker rm -f grandt-frontend || true'

                

                    sh ' docker run -d --name grandt-backend -p 8081:8080 grandt-backend:latest'
                    sh 'docker run -d --name grandt-frontend -p 3000:3000 ${FRONTEND_IMAGE}:latest'
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline completo.'
        }
    }
}
