def call(String APP,String SSH_ID, String GIT_URL, String ENVIRONMENT) {
  pipeline {
   agent any
   //parameters {
    //string defaultValue: 'git_ssh', description: 'SSH KEY ID', name: 'SSH_ID', trim: true
    //string defaultValue: '', description: 'GIT Url Repository - SSH', name: 'GIT_URL', trim: true
    //string defaultValue: '', description: 'Environment', name: 'ENVIRONMENT', trim: true
  //}
   stages {
     stage('Validating Terraform binary') {
       steps {
         sh "if [[ \$(which terraform) > 0 ]] ; then return -1; else echo 'Terraform available' ; fi"
       }
     }
     stage('Checkout')
     {
         steps{
          git credentialsId: "${SSH_ID}", url: "${GIT_URL}"
         }
     }
     stage('Terraform Init')
     {
       steps{
            sh 'ls -lrt'
            //sh 'cd ${ENVIRONMENT}/ && terraform init'
       }
     }
     stage('Terraform Plan')
     {
       steps{
            sh 'cd ${ENVIRONMENT}/ && terraform plan -out=${APP}-${ENVIRONMENT}.plan'
          }
     }
     stage('Terraform Apply')
     {
       steps{
            sh 'cd ${ENVIRONMENT}/ && terraform plan -out=${APP}-${ENVIRONMENT}.plan'
          }
     }
   }
  }
}
