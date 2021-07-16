# WeatherNOW

The idea of this small project is to fetch data from some API and display it with some sort of animations. I decided to go for [OpenWeatherMap API](https://openweathermap.org/)
wich is an forecast/current weather public API

Main topics for its development:
* [Retrofit](https://square.github.io/retrofit/) for requests
* [Lottie's animations](https://lottiefiles.com/) AIRBNB public API for animations. I got all weather animations from it
* [Hilt dependency injection](https://developer.android.com/training/dependency-injection/hilt-android?hl=en-us) (I used interface binding this time)
* [ViewModel Overview](https://developer.android.com/topic/libraries/architecture/viewmodel) turns out that activityViewModels are very easy to use. If you don't know what they do, READ THE LINK! Just kidding, they are needed in case you want to share info (live data!) between activities/fragments with your view model class
* [JSON To Kotlin Class](https://plugins.jetbrains.com/plugin/9960-json-to-kotlin-class-jsontokotlinclass-) always very helpful
* [Weather conditions](https://openweathermap.org/weather-conditions) if you are interested about this amazing API
* [Get the last known location](https://developer.android.com/training/location/retrieve-current) this is how I get the current coordinates. The documentation says "In some rare situations this can be null" but its not that rare. It can happen a lot. In case you are always getting null, restarting your localization on your settings seems to solve it
* [Permissions on Android](https://developer.android.com/guide/topics/permissions/overview) its good to know how to configure real-time permissions request and how to handle it in case the user doesn't accept it

|<img src="https://user-images.githubusercontent.com/66192808/125645531-68138639-3745-462d-939d-b0430a1f7906.gif" width="24%" height="22%"/>
<img src="https://user-images.githubusercontent.com/66192808/125645523-25ad709f-e1b7-4203-8bfe-5df082ec80a0.gif" width="24%" height="22%"/>
<img src="https://user-images.githubusercontent.com/66192808/125646716-14a48e3f-7eb3-44c8-be2e-9f70d6194683.gif" width="24%" height="22%"/>
<img src="https://user-images.githubusercontent.com/66192808/125642707-cf4a48d6-f1db-4466-91d1-95c9a56d0058.gif" width="24%" height="22%"/>|

|<img src="https://user-images.githubusercontent.com/66192808/125524848-e9b5ee81-785f-44f7-b5ac-3735551763f9.gif" width="24%" height="22%"/>
<img src="https://user-images.githubusercontent.com/66192808/125645526-b2be62a5-909c-45dd-a414-018c4f55ea49.gif" width="24%" height="22%"/>|
