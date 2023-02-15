package com.udacity.asteroidradar.Room

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.Asteroid


@Dao
interface DataBaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertAllAst(asteroid: List<Asteroid>)


    @Query("SELECT * FROM AsteroidTable ORDER BY closeApproachDate")
    fun getAllAst(): LiveData<List<Asteroid>>

    @Query("SELECT * FROM AsteroidTable WHERE closeApproachDate= :today")
    fun gettoday(today:String): LiveData<List<Asteroid>>

    @Query("SELECT * FROM AsteroidTable WHERE closeApproachDate >= :today ORDER BY closeApproachDate")
    fun getweek(today:String): LiveData<List<Asteroid>>

    @Query("DELETE FROM AsteroidTable where closeApproachDate < :today")
    suspend fun clear(today:String)

}


@Database(entities = [Asteroid::class], version = 1, exportSchema = false)
abstract class DataBaseAsteroid:RoomDatabase()
{
    abstract val database:DataBaseDao
    companion object {
        private var Instance: DataBaseAsteroid? = null
        fun getDatabase(context: Context): DataBaseAsteroid {
            var instance= Instance
            synchronized(this) {
                var instance= Instance
                if (instance==null)
                {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DataBaseAsteroid::class.java,
                        "AsteroidRadar"
                    ).fallbackToDestructiveMigration().build()
                    Instance=instance
                }
                return instance
            }

        }
    }

}