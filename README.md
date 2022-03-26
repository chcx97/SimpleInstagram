# Project 3 - *Instagram*

**SimpleInstagram** is a photo sharing app similar to Instagram but using Parse as its backend.

Time spent: **12** hours spent in total

## User Stories

The following **required** functionality is completed:

- [x] User can sign up to create a new account using Parse authentication.
- [x] User can log in and log out of his or her account.
- [x] The current signed in user is persisted across app restarts.
- [x] User can take a photo, add a caption, and post it to "Instagram".

The following **optional** features are implemented:

- [ ] User sees app icon in home screen and styled bottom navigation view
- [ ] Style the feed to look like the real Instagram feed.
- [ ] After the user submits a new post, show an indeterminate progress bar while the post is being uploaded to Parse.

The following **additional** features are implemented:

- [ ] List anything else that you can get done to improve the app functionality!

## Video Walkthrough

Here's a walkthrough of implemented user stories:

**Signing in a user, making a new post, and logging out user**


<img src='walkthrough_simpleInsta.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />


![Alt text](https://github.com/chcx97/SimpleInstagram/blob/master/Post.JPG)

**Signing up a new user**

<img src='walkthrough_simpleInsta4.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

![Alt text](https://github.com/chcx97/SimpleInstagram/blob/master/SignupUser.JPG)

**Current user is still signed in after app restarts**

<img src='walkthrough_simpleInsta6.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

This overall project was very difficult for me because I ran into various problems. One of these problems were trying to debug my code when I ran into an error. Because this project was much bigger than the previous ones, running into errors were more likely to happen for me. 
For example, my function for submitting a post was running into an error when I included the method onLaunchCamera(). During this time, I was afraid that the error was because I followed the steps in the walkthrough incorrectly for the
MainActivity file. However, when I was reviewing the console app, I noticed that there was an error because I set the image in the Post kotlin file as KEY_DESCRIPTION instead of KEY_IMAGE. After realizing my mistake, I immediately fixed it. Although this was a challenging project,
I learned a lot about parse, bitmaps (from when asked to resize image) and had more practice in understanding intents and onClickListeners. 

## Open-source libraries used

- [Android Async HTTP](https://github.com/codepath/CPAsyncHttpClient) - Simple asynchronous HTTP requests with JSON parsing
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Android

## License

    Copyright [2022] [Christy Xiong]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
