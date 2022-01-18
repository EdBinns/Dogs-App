package com.edbinns.dogsapp.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.edbinns.dogsapp.R
import com.edbinns.dogsapp.services.repository.DogsRepository
import com.edbinns.dogsapp.services.repository.FactsRepository
import com.edbinns.dogsapp.utils.Constants.CHANNEL_ID
import com.edbinns.dogsapp.utils.Constants.CHANNEL_NAME
import com.edbinns.dogsapp.view.activitys.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationUtils @Inject constructor(private val repository: FactsRepository) {


    private fun showNotification(context: Context, showText : String){
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        val notification = NotificationCompat.Builder(context,CHANNEL_ID)
            .setContentTitle("Sabias que...")
            .setContentText(showText)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.dog_face)
            .build()
        val notificationManager = NotificationManagerCompat.from(context)
        //.setSmallIcon()

        notificationManager.notify(Constants.NOTIFICATION_ID,notification)
    }

    private fun showFactNotification(context: Context){

        val myHandler = Handler(Looper.getMainLooper())

//        myHandler.post(object : Runnable {
//            override fun run() {
//                CoroutineScope.launch(Dispatchers.IO){
//
//                }
//
//                myHandler.postDelayed(this, 5000 /*5 segundos*/)
//            }
//        })
    }
    fun createNotification(context: Context, showText : String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                lightColor = Color.GREEN
                enableLights(true)
            }

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            manager.createNotificationChannel(channel)

            showNotification(context,showText)
        }
    }
}