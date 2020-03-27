package com.example.pekomon.weatherapp.data.network

import android.util.Log
import com.example.pekomon.weatherapp.BuildConfig
import com.example.pekomon.weatherapp.data.db.entity.CurrentWeatherEntity
import com.example.pekomon.weatherapp.data.network.response.CurrentWeatherResponse
import com.example.pekomon.weatherapp.data.network.response.FutureWeatherResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


// https://api.openweathermap.org/data/2.5/weather?q=London&appid=ace55638ded934d787a3f89c6ccfcc4f&lang=fi&units=metric


// http://api.openweathermap.org/data/2.5/forecast?q=london&units=metric&appid=ace55638ded934d787a3f89c6ccfcc4f&cnt=1
interface OpenWeatherMapApiService {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") location: String,
        @Query("lang") languageCode: String = "en",
        @Query("units") unitsFormat: String = "metric"
    ) : CurrentWeatherResponse

    @GET("weather")
    suspend fun getCurrentWeatherByCoords(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("lang") languageCode: String = "en",
        @Query("units") unitsFormat: String = "metric"
    ) : CurrentWeatherResponse

    @GET("forecast")
    suspend fun getFutureWeather(
        @Query("q") location: String,
        @Query("lang") languageCode: String = "en",
        @Query("units") unitsFormat: String = "metric",
        @Query("cnt") cnt: Int
    ) : FutureWeatherResponse

    @GET("forecast")
    suspend fun getFutureWeatherByCoords(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("lang") languageCode: String = "en",
        @Query("units") unitsFormat: String = "metric",
        @Query("cnt") cnt: Int
    ) : FutureWeatherResponse

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): OpenWeatherMapApiService {
            val requestInterceptor = Interceptor {chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("appid", BuildConfig.APIKEY)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                //.addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherMapApiService::class.java)
        }
    }
}