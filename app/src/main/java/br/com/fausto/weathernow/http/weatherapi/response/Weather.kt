package br.com.fausto.weathernow.http.weatherapi.response

data class Weather(
    var base: String?,
    var clouds: Clouds?,
    var cod: Int?,
    var coord: Coord?,
    var dt: Int?,
    var id: Int?,
    var main: Main?,
    var name: String?,
    var sys: Sys?,
    var timezone: Int?,
    var visibility: Int?,
    var weather: List<WeatherX>?,
    var wind: Wind?
)