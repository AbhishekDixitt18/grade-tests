pipeline {

    agent any

    environment {
        RUNNER_IMAGE = 'pandatabhi18/grade-runner:v1'
        REPORT_DIR = "${WORKSPACE}/docker-reports"
    }

    stages {

        stage('Checkout SCM') {
            steps {
                checkout scm
            }
        }

        stage('Pull Image') {
            steps {
                sh 'docker pull ${RUNNER_IMAGE}'
            }
        }

        stage('Run Tests') {
            steps {

                sh '''
                docker run --rm \
                -v ${WORKSPACE}:/app \
                -w /app \
                ${RUNNER_IMAGE} sh -c "rm -rf target"

                rm -rf ${REPORT_DIR} || true

                mkdir -p ${REPORT_DIR}

                docker run --rm \
                -e MAVEN_CONFIG=/tmp/.m2 \
                -v ${WORKSPACE}:/app \
                -v ${REPORT_DIR}:/app/target/surefire-reports \
                -w /app \
                ${RUNNER_IMAGE} mvn test
                '''
            }
        }

        stage('Publish Results') {
            steps {
                junit 'docker-reports/*.xml'
            }
        }
    }

    post {

        always {
            archiveArtifacts artifacts: 'docker-reports/**', fingerprint: true
        }

        success {
            echo 'Build Passed Successfully!'
        }

        failure {
            echo 'Build Failed!'
        }
    }
}