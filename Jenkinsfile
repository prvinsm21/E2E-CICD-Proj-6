pipeline {
    agent any
    environment {
        DOCKERUSERNAME = "privnsm21"
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
    }
}