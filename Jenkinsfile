pipeline {
  agent any
  stages {
    stage('Build Iris') {
      steps {
        build(job: 'setup', wait: true, quietPeriod: 5)
        build(job: 'Iris', quietPeriod: 5, wait: true)
      }
    }

  }
}