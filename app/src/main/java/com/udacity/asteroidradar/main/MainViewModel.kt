package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.RepoAst
import com.udacity.asteroidradar.Room.DataBaseAsteroid
import kotlinx.coroutines.launch

class Factory(val dataBaseAsteroid: DataBaseAsteroid):ViewModelProvider.Factory
{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(dataBaseAsteroid) as T
    }
}
class MainViewModel(val dataBaseAsteroid: DataBaseAsteroid): ViewModel() {
    val repo=RepoAst(dataBaseAsteroid)


    var pictureOfDay=MutableLiveData<PictureOfDay>()
    init {
        viewModelScope.launch {
            pictureOfDay.value=repo.getimage()
            repo.refresh()
        }
    }

    val _li=MutableLiveData<Asteroid?>(null)
    val li: MutableLiveData<Asteroid?>
        get()=_li
    fun start(asteroid: Asteroid)
    {
     _li.value=asteroid
    }
    fun end()
    {
        _li.value=null
    }
    fun GetToday()
    {
        type.value=FilterType.TODAY
    }
    fun GetWeek()
    {
        type.value=FilterType.WEEK
    }
    fun GetAll()
    {
        type.value=FilterType.SAVED
    }

    val type=MutableLiveData<FilterType>(FilterType.WEEK)

    val list: LiveData<List<Asteroid>> = Transformations.switchMap(type) { type ->
        when (type) {
            FilterType.TODAY -> repo.GetToday()
            FilterType.SAVED -> repo.getall()
            FilterType.WEEK -> repo.GetWeek()
        }
    }

}
enum class FilterType
{
    TODAY,
    SAVED,
    WEEK

}