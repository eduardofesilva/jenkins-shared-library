def call() {
  pipeline {
   agent any
   parameters {
    string defaultValue: 'git', description: 'SSH KEY ID', name: 'SSH_ID', trim: true
    string defaultValue: '', description: 'GIT Url Repository - SSH', name: 'GIT_URL', trim: true
    string defaultValue: '', description: 'Environment', name: 'ENVIRONMENT', trim: true
  }
   stages {
     stage('Validating Terraform binary') {
       steps {
         sh "if [[ \$(which terraform) > 0 ]] ; then return -1; else echo 'Terraform available' ; fi"
       }
     }
     stage('Checkout')
     {
         steps{
          git credentialsId: "${params.SSH_ID}", url: "${params.GIT_URL}"
         }
     }
     stage('Terraform Init')
     {
       steps{
         dir('./terraform/')
          {
            sh 'cd ${params.ENVIRONMENT}/ && terraform init'
          }

       }
     }
   }
  }
}
