/*
 * Prerequisites
 * — RUN_IN_DOCKER=true: Docker on the Jenkins agent + permission to bind-mount workspace folders.
 * — RUN_IN_DOCKER=false: JDK 21, Maven on PATH, and Chrome/Chromium (for Selenium) on that agent.
 * — Optional Jenkins plugins: JUnit for test trend; archiveArtifacts works without extras.
 */

pipeline {
    agent any

    parameters {
        booleanParam(
                name: 'RUN_IN_DOCKER',
                defaultValue: true,
                description: 'Run tests in the repo Docker image (recommended: Chrome + JDK 21). Uncheck only if the agent has JDK 21, Maven, and Chrome/Chromium.'
        )
        string(name: 'BROWSER', defaultValue: '', description: 'Optional: e.g. chrome, firefox (-Dbrowser)')
        string(name: 'BASE_URL', defaultValue: '', description: 'Optional: override site URL (-DbaseUrl)')
    }

    options {
        timestamps()
        buildDiscarder logRotator(numToKeepStr: '20')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Run tests') {
            steps {
                script {
                    def mvnArgs = '-B -Dheadless=true'
                    if (params.BROWSER?.trim()) {
                        mvnArgs += " -Dbrowser=${params.BROWSER.trim()}"
                    }
                    if (params.BASE_URL?.trim()) {
                        mvnArgs += " -DbaseUrl=${params.BASE_URL.trim()}"
                    }

                    if (params.RUN_IN_DOCKER) {
                        def tag = "tutorials-ninja-tests:${env.BUILD_NUMBER}"
                        /* Override ENTRYPOINT so we can pass -Dbrowser / -DbaseUrl; Dockerfile uses ENTRYPOINT ["mvn","test","-Dheadless=true"]. */
                        if (isUnix()) {
                            sh 'mkdir -p reports screenshots'
                            sh "docker build -t ${tag} ."
                            sh """
                            docker run --rm --entrypoint mvn \
                              -v "${env.WORKSPACE}/reports:/app/reports" \
                              -v "${env.WORKSPACE}/screenshots:/app/screenshots" \
                              -w /app ${tag} \
                              clean test ${mvnArgs}
                            """.trim()
                        } else {
                            bat 'if not exist reports mkdir reports'
                            bat 'if not exist screenshots mkdir screenshots'
                            bat "docker build -t ${tag} ."
                            bat "docker run --rm --entrypoint mvn -v \"${env.WORKSPACE}\\reports:/app/reports\" -v \"${env.WORKSPACE}\\screenshots:/app/screenshots\" -w /app ${tag} clean test ${mvnArgs}"
                        }
                    } else if (isUnix()) {
                        sh "mvn clean test ${mvnArgs}"
                    } else {
                        bat "mvn clean test ${mvnArgs}"
                    }
                }
            }
        }
    }

    post {
        always {
            junit testResults: 'target/surefire-reports/**/*.xml', allowEmptyResults: true
            archiveArtifacts artifacts: 'reports/**/*.html,target/surefire-reports/**/*', fingerprint: true, allowEmptyArchive: true
            archiveArtifacts artifacts: 'screenshots/**', fingerprint: true, allowEmptyArchive: true
        }

        failure {
                echo "Tests FAILED — check Extent Report in archived artifacts"
            }
            success {
                echo "All tests PASSED"
            }
    }
}
