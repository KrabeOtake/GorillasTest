def COLOR_MAP = [
    'SUCCESS': 'good', 
    'FAILURE': 'danger',
]

pipeline {
    agent { label 'qtest'}

    stages {
        // The first two stages below are explicitly mentioned so they are reported in Jenkins properly.
        stage('Build app') {
            steps {
                sh "./gradlew assembleDebug"
            }
        }

        stage('Build test app') {
            steps {
                sh "./gradlew testDebug"
            }
        }
        
        stage('Instumental tests') {
            steps {
                //Launch and wait for emulator
				//Test lorem ipsum
                sh "${env.ANDROID_HOME}/emulator/emulator -avd Pixel_5_API_30 & $ANDROID_HOME/platform-tools/adb wait-for-device"  
				sh "${env.ANDROID_HOME}/platform-tools/adb root"
				//Run Espresso tests
                sh "./gradlew connectedAndroidTest --stacktrace"
                }
				post {
                always {
                    //Generate test report
                    junit 'app/build/outputs/androidTest-results/connected/*.xml'
                    //Send results to qTest Manager
                    submitJUnitTestResultsToqTest([apiKey: '742f6bdb-e823-4d50-ac96-762adca2628c', containerID: 584385, containerType: 'release', createTestCaseForEachJUnitTestClass: false, createTestCaseForEachJUnitTestMethod: true, overwriteExistingTestSteps: true, parseTestResultsFromTestingTools: false, projectID: 112602, qtestURL: 'https://gorillastest.qtestnet.com', submitToAReleaseAsSettingFromQtest: true, submitToExistingContainer: false, utilizeTestResultsFromCITool: true])
                    //Send a message to Slack
					slackSend channel: '#jenkins-test', 
                              color: COLOR_MAP[currentBuild.currentResult],
                              message: "*${currentBuild.currentResult}:* Job ${env.JOB_NAME} build ${env.BUILD_NUMBER} \n More info at: ${env.BUILD_URL}"
                }
            }
           
        }
    }
}