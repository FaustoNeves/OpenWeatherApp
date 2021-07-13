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

|<img src="https://user-images.githubusercontent.com/66192808/125524857-b7d81494-bfa7-4dd4-9472-61e4895752c7.gif" width="22%" height="22%"/>
<img src="https://user-images.githubusercontent.com/66192808/125524854-2c3085e0-70b7-4297-8c13-4bf7d57237ae.gif" width="22%" height="22%"/>
<img src="https://user-images.githubusercontent.com/66192808/125524855-310221f7-f41a-4271-9210-a87783d2ad78.gif" width="22%" height="22%"/>
<img src="https://user-images.githubusercontent.com/66192808/125524860-c2450502-f317-4017-89ba-4af32fbfaaa0.gif" width="22%" height="22%"/>
<img src="https://user-images.githubusercontent.com/66192808/125524848-e9b5ee81-785f-44f7-b5ac-3735551763f9.gif" width="22%" height="22%"/>
<img src="https://user-images.githubusercontent.com/66192808/125524862-90cf033f-ce5a-4d27-a9cb-60293c052477.gif" width="22%" height="22%"/>|
