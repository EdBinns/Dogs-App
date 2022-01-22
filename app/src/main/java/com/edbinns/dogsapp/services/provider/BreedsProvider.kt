package com.edbinns.dogsapp.services.provider

import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BreedsProvider @Inject constructor()  {
    var allBreedsJSON: JSONObject? = null
}