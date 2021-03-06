[![Build Status](https://app.bitrise.io/app/e26f64bbf0cb8d28/status.svg?token=FZ7LO_Dr3gxWUiMjPRXTDg&branch=master)](https://app.bitrise.io/app/e26f64bbf0cb8d28) 
[![Code Coverage](https://codecov.io/gh/adrielcafe/ChuckNorrisFacts/branch/master/graph/badge.svg)](https://codecov.io/gh/adrielcafe/ChuckNorrisFacts) 
[![Codebeat](https://codebeat.co/badges/300b1528-f22e-409c-b734-644a4beeca6b)](https://codebeat.co/projects/github-com-adrielcafe-chucknorrisfacts-master) 
[![MIT License](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) 

<h1><img src="https://github.com/adrielcafe/ChuckNorrisFacts/blob/master/app/src/main/res/drawable/state_empty.gif?raw=true">Chuck Norris Facts</h1>

Android app powered by [Chuck Norris facts API](https://api.chucknorris.io/).

Sample APK [here](https://app.bitrise.io/artifact/13367550/p/c965378103d159f37eae06e9dc1d4ba6).

<img src="https://github.com/adrielcafe/ChuckNorrisFacts/blob/master/demo.gif?raw=true" width="350">

### Build with

#### App
* [Kotlin](https://github.com/JetBrains/kotlin)
* [Android Architecture Components](https://github.com/googlesamples/android-architecture-components)
* [RxJava](https://github.com/ReactiveX/RxJava)
* [RxPaper2](https://github.com/pakoito/RxPaper2)
* [Retrofit](https://github.com/square/retrofit)
* [Moshi](https://github.com/square/moshi)
* [Koin](https://github.com/InsertKoinIO/koin)
* [Eiffel](https://github.com/etiennelenhart/Eiffel)
* [Kid Adapter](https://github.com/Link184/KidAdapter)

#### Unit & Android Tests
* [JUnit 4](https://github.com/junit-team/junit4)
* [Robolectric](https://github.com/robolectric/robolectric)
* [Espresso](https://developer.android.com/training/testing/espresso)
* [Barista](https://github.com/SchibstedSpain/Barista)
* [Strikt](https://github.com/robfletcher/strikt)
* [okhttp-client-mock](https://github.com/gmazzo/okhttp-client-mock)

## Build
The project can be build with [Android Studio](https://developer.android.com/studio) or through the command line:
```
./gradlew assembleDebug
```

To build and install on the connected device:
```
./gradlew installDebug
```

## Lint
This project uses [ktlint](https://github.com/pinterest/ktlint) through the [spotless plugin](https://github.com/diffplug/spotless).

To check the formatting:
```
./gradlew spotlessKotlinCheck
```

To apply the recommended formatting:
```
./gradlew spotlessKotlinApply
```

## Test
To run unit tests on your machine:
```
./gradlew testDebugUnitTest
```

To run automated tests on connected devices:
```
./gradlew connectedDebugAndroidTest
```

## Code Coverage
The code coverage report is automatically sent to [Codecov](https://codecov.io/) by [Bitrise CI](https://www.bitrise.io/) every time a new push to the `master` branch is made.

To generate it on your machine:
```
./gradlew jacocoTestDebugUnitTestReport
```
<p align="center">
  <img src="https://codecov.io/gh/adrielcafe/ChuckNorrisFacts/branch/master/graphs/icicle.svg">
  <br>
  <i>Current coverage graph</i>
</p>