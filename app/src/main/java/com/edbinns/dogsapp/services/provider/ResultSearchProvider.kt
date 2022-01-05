package com.edbinns.dogsapp.services.provider

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.edbinns.dogsapp.models.Dog
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResultSearchProvider @Inject constructor() {

    val resultSearch:MutableLiveData<List<Dog>> = MutableLiveData()

    fun setResult(result: List<Dog>) : Boolean{
        resultSearch.postValue(result)
        return true
    }
}