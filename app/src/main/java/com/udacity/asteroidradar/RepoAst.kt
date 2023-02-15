package com.udacity.asteroidradar

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.udacity.asteroidradar.Room.DataBaseAsteroid
import com.udacity.asteroidradar.api.RADARAPI
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class RepoAst(private val dataBaseAsteroid: DataBaseAsteroid) {
    val formatter = SimpleDateFormat("yyyy-MM-dd")
    val clender = Calendar.getInstance()
    val now = formatter.format(clender.time)



    fun getall():LiveData<List<Asteroid>> =dataBaseAsteroid.database.getAllAst()
    fun GetWeek():LiveData<List<Asteroid>> = dataBaseAsteroid.database.getweek(now)
    fun GetToday():LiveData<List<Asteroid>> =dataBaseAsteroid.database.gettoday(now)



    suspend fun refresh() {
        withContext(Dispatchers.IO) {
            clender.add(Calendar.DATE, 7)
            val after = formatter.format(clender.time)
            try {
                val res = RADARAPI.retrofitService.getitems( now , after)
                val x = parseAsteroidsJsonResult(JSONObject(res.string()))
                dataBaseAsteroid.database.insertAllAst(x)

            } catch (e: Exception) {
                Log.i("error",e.message.toString())
            }
        }
    }

    suspend fun getimage(): PictureOfDay {
        var res:PictureOfDay
        withContext(Dispatchers.IO) {
            try {
                res = RADARAPI.retrofitService.getimage()

            } catch (_: Exception) {
                res = PictureOfDay(null, null, null)
            }
        }
        Log.i("mostafa", res.url.toString())
        return res
    }

}