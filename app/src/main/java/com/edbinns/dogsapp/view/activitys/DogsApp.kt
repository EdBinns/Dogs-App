package com.edbinns.dogsapp.view.activitys

import android.app.Application
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DogsApp: Application(){
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
    }
}