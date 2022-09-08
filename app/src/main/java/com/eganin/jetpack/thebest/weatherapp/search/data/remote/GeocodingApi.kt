package com.eganin.jetpack.thebest.weatherapp.search.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GeocodingApi {

    @GET("geo/1.0/direct?q={$CITY_PATH_NAME}")
    suspend fun getCoordFromCity(
        @Path(CITY_PATH_NAME) cityName : String,
        @Query("appid") apiKey : String = API_KEY
    ) : GeocodingDto

    companion object{
        private const val API_KEY = "ac0408e4e39747d67068b81850960e69"
        private const val CITY_PATH_NAME = "city"
    }
}