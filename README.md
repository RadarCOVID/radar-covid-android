# RadarCOVID Android App

<p align="center">
    <a href="https://github.com/RadarCOVID/radar-covid-android/commits/" title="Last Commit"><img src="https://img.shields.io/github/last-commit/RadarCOVID/radar-covid-android?style=flat"></a>
    <a href="https://github.com/RadarCOVID/radar-covid-android/issues" title="Open Issues"><img src="https://img.shields.io/github/issues/RadarCOVID/radar-covid-android?style=flat"></a>
    <a href="https://github.com/RadarCOVID/radar-covid-android/blob/master/LICENSE" title="License"><img src="https://img.shields.io/badge/License-MPL%202.0-brightgreen.svg?style=flat"></a>
</p>

## Introduction

Native Android implementation of RadarCOVID tracing client using [DP3T Android SDK](https://github.com/DP-3T/dp3t-sdk-android)

## Prerequisites

These are the tools used to develop the solution:

- Download and install latest [JDK8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- Download [Android Studio and SDK Tools](https://developer.android.com/studio/index.html)

## Installation and Getting Started

Clone this repository and import into **Android Studio**.

```bash
git clone https://github.com/RadarCOVID/radar-covid-android.git
```

## Building
### Create APK

After you complete the `Gradle` project configuration, you can use `gradlew` executable to build the APK.

- From Android Studio:

Use the Android Studio *Build Variants* button to choose between **production** and **staging** flavors combined with debug and release build types.

To build a debug APK:
1. ***Build*** menu
2. ***Build Bundle(s) / APK(s)*** - ***Build APK(s)***

To build a release signed APK
1. ***Build*** menu
2. ***Generate Signed APK...***
3. Fill in the keystore information *(you only need to do this once manually and then let Android Studio remember it)*

- From command line:

```bash
$ ./gradlew assembleProDebug       // to build a debug APK
$ ./gradlew assembleProRelease     // to build a release signed APK
```

Note: Make sure your Android SDK has the Android Support Repository installed, and that your $ANDROID_HOME environment variable is pointing at the SDK or add a local.properties file in the root project with a sdk.dir=... line.

## Support and Feedback

The following channels are available for discussions, feedback, and support requests:

| Type       | Channel                                                |
| ---------- | ------------------------------------------------------ |
| **Issues** | <a href="https://github.com/RadarCOVID/radar-covid-android/issues" title="Open Issues"><img src="https://img.shields.io/github/issues/RadarCOVID/radar-covid-android?style=flat"></a> |

## Contribute

If you want to contribute with this exciting project follow the steps in [How to create a Pull Request in GitHub](https://opensource.com/article/19/7/create-pull-request-github).

More details in [CONTRIBUTING.md](./CONTRIBUTING.md).

## License

This Source Code Form is subject to the terms of the [Mozilla Public License, v. 2.0](https://www.mozilla.org/en-US/MPL/2.0/).


