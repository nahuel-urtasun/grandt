pipeline {
    agent any

    environment {
        BACKEND_IMAGE = 'grandt-backend'
        FRONTEND_IMAGE = 'grandt-frontend'
        DB_CONTAINER = 'grandt-db'
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
            when {
                changeset "Frontend/**"
            }
            steps {
                dir('Frontend') {
                    sh 'docker build -t ${FRONTEND_IMAGE}:latest .'
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
