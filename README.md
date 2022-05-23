# Test test automation strategy
In my opinion the perfect test automation take a place between the code push by the developer and uploading the app to the store. This includes such aspects:
1. **What to test**?
This includes unit, UI, load and performnce tests. In most casses unit tests are created by Developers as part of the DoD and UI tests are done by QAs or Developers. load and performnce tests are mostly nececcasry if your product focuses on high performance tasks. Assuming the TestCase documentation exists, the first step is to determing the priority of automatisation together with (for example) Product Owners. Then start automating going through HighPriority-LowComplexity -> HighPriority-HighComplexity -> LowPriority-LowComplexity -> LowPriority-HighComplexity
2. **When to test?**
It is always good to run automation tests on Pull Request result before the merge with Develop. If the developer is working on their local branch and wants to merge to the Develop, then the best is to merge the latest develop in the local branch run the tests and then merge to the develop if the tests pass. CI\CD tools make this task much eaasier than doing it manualy. Additionally, with the growth of team composition(output) and teams count it is a good practice to introduce the daily or weekly sanity test runs and weekly or bi-weekly regression test runs.
3. **UI Test framework.** Robot pattern is a good example of framework that allows to scale it and support through time with ease. In addition to the presented example it can include mocking the web calls and memory of states of the application. This will allow to get rid of the extra flackiness caused byt network communication and increase the test run speed if you set up the required app state before the each test. Building the freamwork is one of the first things to do in this scheme but creating and supporting the automation tests ia basically an infinite task and should be approached on a daily basis.
4. **CI\CD.** CI\CD tools allow you to run the tests on remote machines and scale them as you need. you can set it up to track the changes of the repository and spawn the required amount of machines to run the test so that even if you have several developers commiting at once, they will not wait in a queue. It is also possible to upload artifacts to a repository manager (e.g. artifactory, maven etc.) or even sign and upload builds to the store. The time and money cost of setting up a CI\CD pipeline is relatively low, so I would recommend approaching it when having a UI automation framework in a decent shape.
5. **Test management tool.** Such tools like Testrail, Azure TestPlan, qTest etc allow to map each testcase to a corresponding automation test and as a result produce the test suite that will have all the testcases you are left to do manually. This can also be a good spot to gather statistics about what features were most error-prone through time. Test management tools are a good thing to have by itself even for storing the testcases. Assuming it is present prior to any automation, it is good to link the testcases to the correspinding automation tests as you create them.
6. **Device farms.** It is possible to integrate your pipeline with device farms(e.g. SauceLabs) to run the tests on multiple different devices to have a broader coverage of OS versions or screen sizes. It is also possible to make them run in parallel to decrease the run time. Probably, the very last step to introduce since you need an extensive testbase and every other step set up prior to it.
7. **Notifications.** It is important for people to be aware of the test run status whether it passed or failed and give them a quick way to check the results. Slack message is a good option but it can also be something like an email. Super low effort to introduce. To implement as soon as the Pipeline is set up.

I mention a lot of things that are "possible" but not always they are needed. Sometimes the effort of introducing it is greater then the benefit from it. The steps priority is always negotiable and should fit the needs of your product.


# Proof of Concept


## Jet Compose UI Test
I implemented the Robot pattern. This way it is much easier to read the test code and support it when your test suite grows large. And especially when you come back to fix the test after a couple of month after creating it.
- AppTest.kt - a collection of the testsuites to run
- DetailsScreen.kt - use cases for automation tests for the Details screen - 4 use cases
- MyCartScreen.kt - use cases for automation tests for the My Cart screen - 8 use cases
- DetailsRobot.kt - implementation of the tests for Details screen
- HomeRobot.kt - implementation of the tests for Details screen
- MyCartsRobot.kt - implementation of the tests for Details screen

Local run instructions:
1. Clone the project
2. Open the Jetsnack project in Android studio
3. Run the AppTest.kt located in the Jetsnack/app/java/com/example/jetsnack/AppTest.kt

(Almost)Remote run instructions:
1. Ping me at cheshirekrab@gmail.com
2. Wait for me to respond that I launched Jenkins =) 
3. Wait for the results.

## Jenkins
Launched and connected a local Jenkins pipeline. It's set up to trigger a build after each push. The build steps and stages are described in Jenkinsfile.
There are 3 stages defined in Jenkinsfile:
- Build app
- Build test app
- Instrumented test

Inside the last stage we launch the emulator, wait for it to boot and launching the test run.
![Jenkins screenshot](https://i.ibb.co/cYFKNf2/photo-2022-05-23-19-15-44.jpg)

As a post-build action we generate a jUnit report and upload it to qTest:
![Jenkins screenshot](https://i.ibb.co/YLsq3LV/photo-2022-05-23-19-15-47.jpg)

## qTest
The jUnit results of instrumented tests are uploaded to the qTest Manager [here](https://gorillastest.qtestnet.com/) inside a post-build action:
![qTest screenshot](https://i.ibb.co/symcLYF/photo-2022-05-23-19-15-49.jpg)

## Slack notification
As a last step of psot-build action, we send a slack message to our #jenkins-test channel to notify developers about the build status:
 
![Slack screenshot](https://i.ibb.co/0rzhnjx/photo-2022-05-23-19-15-42.jpg)

## Youtube demo
https://youtu.be/CzZi9FWpFNc

