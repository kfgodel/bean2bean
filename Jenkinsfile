pipeline {
  agent any

  triggers {
    pollSCM('@daily')
  }
  options {
    // Hace que si hay pasos en paralelo y uno falla, todo el bloque paralelo falle
    parallelsAlwaysFailFast()
    buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '1')) // Conserva las ultimas 10 ejecuciones pero solo la ultima con su workspace
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
    stage('Build'){
      steps {
        sh "mvn verify source:jar -Dgpg.passphrase=$GPG_PASSPHRASE -e -U"
      }
    }
    stage('Post-Build'){
      parallel {
        stage('Deploy'){
          input {
            message "Should we continue?"
            ok "Yes, we should."
            parameters {
              string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?')
              choice(name: 'CHOICES', choices: ['one', 'two', 'three'], description: 'a choice')
            }
          }
          steps {
            echo "sh mvn deploy -e ${PERSON} ${CHOICES}"
          }
        }
        stage('Sonar') {
          tools {
            jdk 'Jdk_11' // SonarScanner will require Java 11+ to run starting in SonarQube 8.x
          }
          steps {
            withSonarQubeEnv(installationName: 'Sonar@IkariSrv02') {
              sh "mvn $SONAR_MAVEN_GOAL -Dsonar.host.url=$SONAR_HOST_URL -e"
            }
          }
        }
      }
    }
  }
}