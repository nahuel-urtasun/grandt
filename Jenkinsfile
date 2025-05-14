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
                git url: 'https://github.com/nahuel-urtasun/grandt.git', branch: 'master', changelog: false, poll: false, credentialsId: 'tu-credential-id', scriptPath: 'Jenkinsfile', sparseCheckoutList: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/nahuel-urtasun/grandt.git', credentialsId: 'tu-credential-id']], reference: '', depth: 1, lfs: false, name: '', quiet: false, tags: false, timeout: 0, sparseCheckout: false, cloneOption: [depth: 1, noCheckout: false, reference: ''], delegate: false, directory: 'repo'
            }
        }

        stage('Construir Backend') {
            steps {
                dir('repo/Backend') {
                    sh 'docker build -t ${BACKEND_IMAGE}:latest .'
                }
            }
        }

        stage('Construir Frontend') {
            steps {
                dir('repo/Frontend') {
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

                    // Crear volumen para SQL y CSV
                    sh 'docker volume create csv-volume'

                    // Listar el contenido del directorio /grandt dentro del contenedor Alpine
                    echo '--- Listando el contenido de /grandt dentro del contenedor Alpine ---'
                    sh '''
                        docker run --rm -v ${WORKSPACE}/repo:/grandt alpine ls -l /grandt
                    '''
                    echo '--- Fin de la lista de /grandt ---'

                    // Copiar init.sql y players.csv al volumen
                    sh '''
                        docker run --rm \
                        -v csv-volume:/data \
                        -v ${WORKSPACE}/repo:/grandt \
                        alpine sh -c "cp /grandt/init.sql /data/init.sql && cp /grandt/players.csv /data/players.csv"
                    '''

                    // Levantar PostgreSQL y montar el volumen en el punto de entrada
                    sh '''
                        docker run -d --name ${DB_CONTAINER} \
                        -e POSTGRES_PASSWORD=mysecretpassword \
                        -p 5432:5432 \
                        -v csv-volume:/docker-entrypoint-initdb.d \
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
    }

    post {
        always {
            echo 'Pipeline completo.'
        }
    }
}
