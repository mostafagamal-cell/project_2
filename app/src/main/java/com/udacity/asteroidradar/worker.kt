package com.udacity.asteroidradar

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bumptech.glide.load.HttpException
import com.udacity.asteroidradar.Room.DataBaseAsteroid.Companion.getDatabase
import java.text.SimpleDateFormat
import java.util.*

class RefreshDataWorker(appContext: Context, params: WorkerParameters):

    CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }
    override suspend fun doWork(): Result {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val clender = Calendar.getInstance()
        val now = formatter.format(clender.time)
        val database = getDatabase(applicationContext)
        database.database.clear(now)
        val repository = RepoAst(database)
        return try {
            repository.refresh()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }

    }
}