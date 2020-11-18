#!groovy

pipeline {
  agent none
  stages {
    stage('Maven Install') {
      agent {
        docker {
          image 'maven:3.5.0'
        }
      }
      steps {
        sh 'mvn clean install'
      }
    }

      stage('Performance Test') {
          agent {
              docker { image maven:3-alpine }
          }
          steps {
              sh: mvn gatling:test
          }
      }
    stage('Docker Build') {
      agent any
      steps {
        sh 'docker build -t vab919/pet-project:latest .'
      }
    }
    stage('Docker Push and Deploy') {
      agent any
      steps {
        withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
          sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
          sh 'docker push vab919/pet-project:latest'
          sh 'docker run --name pet-clinic -p 8081:8081 vab919/pet-project:latest'
        }
      }
    }
  }
}
