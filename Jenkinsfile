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

    stage('Docker Build and Run') {
      agent any
      steps {
        sh 'docker build -t vab919/pet-project:latest .'
          sh 'docker stop pet-clinic'
          sh 'docker rm pet-clinic'
        sh 'docker run -d  --name pet-clinic -p 8081:8081 vab919/pet-project:latest'

      }
    }

     stage("Run Gatling") {
         agent {
         steps {
             sh 'mvn gatling:test'
         }
         post {
             always {
                 gatlingArchive()
             }
         }
     }
     }
    stage('Docker Push') {
      agent any
      steps {
        withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
          sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
          sh 'docker push vab919/pet-project:latest'
        }
      }
    }
  }
}
