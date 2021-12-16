package com.example.notificationsdemo.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.notificationsdemo.data.Content

// Create a class as Notification Util also create a function to create notification channel.
class Notification {

    fun createInboxStyleNotificationChannel(context: Context): String {

        // NotificationChannels are required for Notifications on O (API 26) and above.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // The id of the channel.
            val channelId: String = Content.mChannelId

            // The user-visible name of the channel.
            val channelName: CharSequence = Content.mChannelName

            // The user-visible description of the channel.
            val channelDescription: String = Content.mChannelDescription
            val channelImportance: Int = Content.mChannelImportance
            val channelEnableVibrate: Boolean = Content.mChannelEnableVibrate
            val channelLockscreenVisibility: Int =
                Content.mChannelLockscreenVisibility

            // Initializes NotificationChannel.
            val notificationChannel = NotificationChannel(channelId, channelName, channelImportance)
            notificationChannel.description = channelDescription
            notificationChannel.enableVibration(channelEnableVibrate)
            notificationChannel.lockscreenVisibility = channelLockscreenVisibility

            // Adds NotificationChannel to system. Attempting to create an existing notification
            // channel with its original values performs no operation, so it's safe to perform the
            // below sequence.
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)

            return channelId

        } else {

            // Returns null for pre-O (26) devices.
            return ""

        }
    }
}