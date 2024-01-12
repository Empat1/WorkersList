package com.example.workerslist.presentation.notification

import android.Manifest
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.workerslist.R
import javax.inject.Inject

class NotificationBuilder @Inject constructor(
    private val application: Application
) {
    fun standardNotification(): NotificationCompat.Builder {
        val id = application.getString(R.string.notification_channel_id_standard)
        val title = application.getString(R.string.notification_title)
        val text = application.getString(R.string.notification_save_date)

        createChannel(id, "Standard")

        return NotificationCompat.Builder(application, id)
            .setSmallIcon(R.drawable.ic_add)
            .setContentTitle(title)
            .setContentText(text)

    }

    private fun createChannel(id: String, name:String){
        val mChannel = NotificationChannel(
            id,
            name,
            NotificationManager.IMPORTANCE_LOW
        )

        val notificationManager = application.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)
    }

    fun foregroundInfoNotification(progress: String): NotificationCompat.Builder {
        val id = application.getString(R.string.notification_channel_id)
        val title = application.getString(R.string.notification_title)

        createChannel(id, "Foreground")

        return NotificationCompat.Builder(application, id)
            .setContentTitle(title)
            .setTicker(title)
            .setContentText(progress)
            .setSmallIcon(R.drawable.ic_add)
            .setOngoing(true)
    }

    fun sendMessage(notificationBuilder: NotificationCompat.Builder) {
        if (ActivityCompat.checkSelfPermission(
                application,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        NotificationManagerCompat.from(application).notify(1, notificationBuilder.build())
    }
}