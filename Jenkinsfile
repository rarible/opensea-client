@Library('shared-library') _

pipeline {
	agent any

	options {
		disableConcurrentBuilds()
	}

	stages {
		stage('deploy') {
			when {
                anyOf { branch 'master'; branch 'release/*' }
			}
			steps {
				sh 'mvn clean'
                deployToMaven('nexus-ext-ci')
			}
		}
	}
}
