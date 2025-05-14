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
            steps {
                dir('Frontend') {
                    sh 'docker build -t ${FRONTEND_IMAGE}:latest .'
                }
            }
        }

       stage('Levantar Contenedores') {
    steps {
        script {
            // Eliminar contenedores existentes
            sh 'docker rm -f grandt-backend || true'
            sh 'docker rm -f grandt-frontend || true'
            sh 'docker rm -f grandt-db || true'

            // Levantar PostgreSQL y montar el volumen en el punto de entrada
            sh '''
                docker run -d --name ${DB_CONTAINER} \
                -e POSTGRES_PASSWORD=mysecretpassword \
                -p 5432:5432 \
                -v grandt-data:/docker-entrypoint-initdb.d \
                postgres:latest
            '''

            // Esperar a que PostgreSQL se inicialice correctamente
            sh 'sleep 20'

            // Levantar backend
            sh '''
                docker run -d --name grandt-backend -p 8081:8080 \
                --link ${DB_CONTAINER}:db \
                ${BACKEND_IMAGE}:latest
            '''

            // Levantar frontend
            sh '''
                docker run -d --name grandt-frontend -p 3000:3000 \
                ${FRONTEND_IMAGE}:latest
            '''
        }
    }
}

    post {
        always {
            echo 'Pipeline completo.'
        }
    }
}
