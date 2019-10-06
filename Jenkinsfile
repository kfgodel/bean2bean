pipeline {
  agent any

  triggers {
    pollSCM('@daily')
  }
  options {
    // Hace que si hay pasos en paralelo y uno falla, todo el bloque paralelo falle
    parallelsAlwaysFailFast()
  }

  tools {
    maven 'Mvn_3.6.2'
    jdk "Jdk_1.8.221"
  }

  stages {
    stage('Clean') {
      steps {
        sh 'mvn clean'
      }
    }
    stage('Deploy'){
      steps {
        sh "mvn deploy source:jar -Dgpg.passphrase=$GPG_PASSPHRASE -e -U"
      }
    }
    stage('Sonar') {
      steps {
        withSonarQubeEnv(installationName: 'Sonar@IkariSrv02') {
          sh "mvn $SONAR_MAVEN_GOAL -Dsonar.host.url=$SONAR_HOST_URL -e"
        }
      }
    }
  }
}