package com.udacity.asteroidradar.api

import com.squareup.moshi.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.PictureOfDay
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))

    .baseUrl(BASE_URL)
    .build()
interface RADARINTERFACE
{
    @GET("neo/rest/v1/feed")
      suspend fun getitems(
                 @Query("start_date")strat:String
                ,@Query("end_date")end:String,
                @Query("api_key") key:String="xu5I7Me1hIW3cGVfY5lMOUVSzisV7p2FyhGfN0yC"):
                    ResponseBody

    @GET("planetary/apod")
    suspend fun getimage(@Query("api_key")
                             key:String="xu5I7Me1hIW3cGVfY5lMOUVSzisV7p2FyhGfN0yC"):PictureOfDay

}
object RADARAPI {
    val retrofitService : RADARINTERFACE by lazy {
        retrofit.create(RADARINTERFACE::class.java)
    }
}