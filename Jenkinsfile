pipeline {
    agent any
    environment {
        DOCKERUSERNAME = "privnsm21"
        DOCKERCREDENTIALS = credentials('dockerhub')
        DOCKERIMAGE_NAME = "prvinsm21/sentra-site:${BUILD_NUMBER}"
    }

    stages {
        stage ('Git Checkout') {
            steps {
                sh 'echo Passed'
            }
        }
        stage ('Unit testing') {
            steps {
                sh 'mvn test'
            }
        }
        stage ('Integration testing'){
            steps {
                sh 'mvn clean verify -DskipUnitTests'
            }
        }
        stage ('Build stage') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage ('Static code anaysis') {
            steps {
                script {
                    withSonarQubeEnv(credentialsId: 'sonar-api') {
                sh 'mvn clean package sonar:sonar'
                    }
                }
            }
        }
        stage ('Quality gate status') {
            steps {
                script {
                    waitForQualityGate abortPipeline: false, credentialsId: 'sonar-api'
                }
            }
        }
        stage ('Build and Push Docker image') {
            steps {
                script {
                    sh 'docker build -t ${DOCKERIMAGE_NAME} .'
                    def dockerImage = docker.image("${DOCKERIMAGE_NAME}")
                    docker.withRegistry('https://index.docker.io/v1/', "dockerhub") {
                    dockerImage.push()
                    }
                }
            }
        }
        stage('Update Deployment File') {
        environment {
            GIT_REPO_NAME = "E2E-CICD-Proj-6"
            GIT_USER_NAME = "prvinsm21"
        }
        steps {
            withCredentials([string(credentialsId: 'git-push', variable: 'GITHUB_TOKEN')]) {
                sh '''
                    git config user.email "prvinsm21@gmail.com"
                    git config user.name "Macko"
                    BUILD_NUMBER=${BUILD_NUMBER}
                    sed -i "s/replaceImageTag/${BUILD_NUMBER}/g" manifests/deployment.yml
                    git add manifests/deployment.yml
                    git commit -m "Update deployment image to version ${BUILD_NUMBER}"
                    git push https://${GITHUB_TOKEN}@github.com/${GIT_USER_NAME}/${GIT_REPO_NAME} HEAD:master
                '''
                }
            }
        }   
    }
}