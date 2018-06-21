# UdacityAndroidNanodegree
This project contains all tasks which I have to solve in my Android Nanodegree at Udacity.

## About the repo
The `master` branch contains only **this** README. 
All other projects are resident in another branch.

If I have to create a new task a new branch will be created with 
```
git checkout --orphan new-branch-name
```
> Branch names should be in named with dashes!

All projects should are linked in the [table below](#projects) 👇

## Rules
To provide some consistency I have some rules which should* be applied for **all** projects.

Each project has:
* a README which quickly explain what it is and what was todo
* only one single commit with the message `Init commit`
* a compile & targetSdkVersion of `27`
* a minSdkVersion of `21`
* a Gradle version of `4.5.1`
* at least the following dependencies: [`appcompat-v7`, `constraint-layout`](https://developer.android.com/topic/libraries/support-library/setup.html), [`recyclerview`](https://developer.android.com/guide/topics/ui/layout/recyclerview.html)
* at least the following test-dependencies: [`junit`](https://mvnrepository.com/artifact/junit/junit/), [`mockito-core`](https://mvnrepository.com/artifact/org.mockito/mockito-core), [`assertj-core`](https://mvnrepository.com/artifact/org.assertj/assertj-core)
* at least the following android-test-dependencies: [`runner`, `rule`](https://developer.android.com/training/testing/unit-testing/instrumented-unit-tests.html) (as well as the test-dependencies of course)

> *Not all projects will strictly follow these rules. Bust most of them!

## Submit and Binary
After a project is finished it has to be submitted to a review in the Nanodegree.
This could be done via open GitHub repository or as a *.zip file.

Even if I have **this** repository to track all of my projects I do prefer to upload a *.zip.
To make sure that each project can be upload as a *.zip it should be [cleaned](https://docs.google.com/document/d/1eYvuXY7GRE6VQpq4Rp-KotU1ti-JEySN1KdyKwjhzEQ/pub?embedded=true) first.

After I uploaded the *.zip I have to add the **same** file into **this** repo to the [**submitted_binaries** branch](https://github.com/StefMa/UdacityAndroidNanodegree/tree/submitted_binaries).
For that I have to create a new directory with the same name as the branch name and put the file in it.

It is possible that my code got rejected and should be changed.
If that will happen I don't override the file but add a new one named like the original plus the prefix `_reviewX` (while X stands for a number).

## Projects

| Name | Branch | Binary | Description | Task |
|-|-|-|-|-|
| Sandwich Club | [sandwich-club-starter-code](https://github.com/StefMa/UdacityAndroidNanodegree/tree/sandwich-club-starter-code) | [Binary](https://github.com/StefMa/UdacityAndroidNanodegree/tree/submitted_binaries/sandwich-club-starter-code) | See Sandwiches in a list and open their details | Fix JSON Reader (without a lib) and fix Detail View |
| Popular Movies Stage 1 | [popular-movies-stage-1](https://github.com/StefMa/UdacityAndroidNanodegree/tree/popular-movies-stage-1) | [Binary](https://github.com/StefMa/UdacityAndroidNanodegree/tree/submitted_binaries/popular-movies-stage-1) | Show movies which can be sorted by popularity & top rated | Show a list of movies which can be toggled (between popularity & top rated) and their details |
| Popular Movies Stage 2 | [popular-movies-stage-2](https://github.com/StefMa/UdacityAndroidNanodegree/tree/popular-movies-stage-2) | [Binary](https://github.com/StefMa/UdacityAndroidNanodegree/tree/submitted_binaries/popular-movies-stage-2) | Add videos & reviews to the details. Additionally movies can be selected as favorite | Shows the videos & review which are belong to the movies. Add favorite function which have to be stored in a [ContentProvider](https://developer.android.com/reference/android/content/ContentProvider.html) |
| Baking App | [baking-app](https://github.com/StefMa/UdacityAndroidNanodegree/tree/baking-app) | [Binary](https://github.com/StefMa/UdacityAndroidNanodegree/tree/submitted_binaries/baking-app) | Create a Baking App which shows some recipes and their ingredients and steps | Get recipes from a network resource. Navigate between steps. Provide a [App Widget](https://developer.android.com/guide/topics/appwidgets/). Test the UI with [Espresso](https://developer.android.com/training/testing/ui-testing/espresso-testing) |
| Build it bigger | [build-it-bigger](https://github.com/StefMa/UdacityAndroidNanodegree/tree/build-it-bigger) | [Binary](https://github.com/StefMa/UdacityAndroidNanodegree/tree/submitted_binaries/build-it-bigger) | Get jokes from a Cloud Endpoint. Create a Free & Paid flavor | Get jomes from a [Google Cloud Endpoints](https://cloud.google.com/endpoints/). Create a java and a android library. Create a free flavor without Ads and a paid flavor with Ads |
| Make your App Material | [make-your-app-material](https://github.com/StefMa/UdacityAndroidNanodegree/tree/make-your-app-material) | [Binary](https://github.com/StefMa/UdacityAndroidNanodegree/tree/submitted_binaries/make-your-app-material) | Make the App look Material | Grep a starter code and change the UI so that it looks Material|
| | | | | |
