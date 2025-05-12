pipeline {
    agent any

    environment {
        BACKEND_IMAGE = 'grandt-backend'
        FRONTEND_IMAGE = 'grandt-frontend'
        DB_CONTAINER = 'grandt-db'  // Contenedor de base de datos
        CSV_FILE_PATH = '/home/nwtn/GranDT/players.csv'  // Ruta del archivo CSV en tu máquina local
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
                    // Eliminar contenedores existentes si están corriendo
                    sh 'docker rm -f grandt-backend || true'
                    sh 'docker rm -f grandt-frontend || true'
                    sh 'docker rm -f grandt-db || true'  // Eliminar contenedor de DB si ya existe

                    // Crear volumen para compartir el archivo CSV con el contenedor de PostgreSQL
                    sh 'docker volume create csv-volume'

                    // Copiar el archivo CSV al volumen de Docker
                    sh 'docker run --rm -v csv-volume:/data alpine cp ${CSV_FILE_PATH} /data/players.csv'

                    // Levantar contenedor de la base de datos (ejemplo con PostgreSQL)
                    sh '''
                        docker run -d --name ${DB_CONTAINER} -e POSTGRES_PASSWORD=mysecretpassword -p 5432:5432 \
                        -v csv-volume:/docker-entrypoint-initdb.d \
                        postgres:latest
                    '''

                    // Esperar a que el contenedor de DB esté listo
                    sh 'sleep 20'

                    // Levantar contenedor del backend
                    sh 'docker run -d --name grandt-backend -p 8081:8080 --link ${DB_CONTAINER}:db ${BACKEND_IMAGE}:latest'

                    // Levantar contenedor del frontend
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



