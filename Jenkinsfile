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
				//Test lorem ipsu
                sh "${env.ANDROID_HOME}/emulator/emulator -avd Pixel_5_API_30 & $ANDROID_HOME/platform-tools/adb wait-for-device"  
				sh "${env.ANDROID_HOME}/platform-tools/adb root"
				//Run Espresso tests
                sh "./gradlew connectedAndroidTest --stacktrace"
                }
           
        }
    }
}