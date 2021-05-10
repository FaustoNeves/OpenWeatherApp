package br.com.fausto.weathernow

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var textCity: EditText
    lateinit var textCountry: EditText
    lateinit var textResult: TextView
    private val url = "http://api.openweathermap.org/data/2.5/weather"
    private val apiKey = BuildConfig.API_KEY
    var outPut: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textCity = findViewById(R.id.city)
        textCountry = findViewById(R.id.country)
        textResult = findViewById(R.id.result)
    }

    fun getWeather(view: View) {
        var tempUrl = ""
        outPut = ""
        var city = textCity.text.toString()
        var country = textCountry.text.toString()
        if (city == "") {
            textResult.text = "City field is empty"
        } else {
            if (country == "") {
                tempUrl = "$url?q=$city,$country&appid=$apiKey"
            } else {
                tempUrl = "$url?q=$city&appid=$apiKey"
            }

            val queue = Volley.newRequestQueue(this)

            val stringRequest = StringRequest(Request.Method.POST, tempUrl,
                { response ->
                    var jsonResponse = JSONObject(response)
                    var jsonArray = jsonResponse.getJSONArray("weather")
                    var jsonObjectWeather = jsonArray.getJSONObject(0)
                    var description = jsonObjectWeather.getString("description")
                    var jsonObjectMain = jsonResponse.getJSONObject("main")
                    var temp = jsonObjectMain.getDouble("temp") - 273.15
                    var feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15
                    var pressure = jsonObjectMain.getInt("pressure")
                    var humidity = jsonObjectMain.getInt("humidity")
                    var jsonObjectWind = jsonResponse.getJSONObject("wind")
                    var wind = jsonObjectWind.getString("speed")
                    var jsonObjectClouds = jsonResponse.getJSONObject("clouds")
                    var clouds = jsonObjectClouds.getString("all")
                    var jsonObjectSys = jsonResponse.getJSONObject("sys")
                    var countryName = jsonObjectSys.getString("country")
                    var cityName = jsonResponse.getString("name")
                    outPut += " Current weather of " + cityName + " (" + countryName + ")" + "\n Temp: " + String.format(
                        "%.1f",
                        temp
                    ) + " °C" + "\n Feels like: " + String.format(
                        "%.1f",
                        feelsLike
                    ) + " °C" + "\n Humidity: " + humidity + " \n Description: " +
                            "" + description + "\n Wind Speed: " + wind + "\n Cloudiness: " + clouds + "%" + "\n Pressure: " + pressure + "hPa"
                    textResult.text = outPut

                },
                {
                    Log.e("something went wrong!", it.toString())
                    textResult.text = "Unknown city"
                })

            queue.add(stringRequest)
        }
    }
}