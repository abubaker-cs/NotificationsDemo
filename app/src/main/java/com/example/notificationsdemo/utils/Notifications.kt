package com.example.notificationsdemo.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.notificationsdemo.data.Content

// Create a class as Notification Util also create a function to create notification channel.
class Notifications {

    fun createInboxStyleNotificationChannel(context: Context): String {

        // Note: NotificationChannels are required for Notifications on O (API 26) and above.

        // 0 (API 26) - Released publicly as Android 8.0 in August 2017.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // The id of the channel.: channel_email_1
            val channelId: String = Content.mChannelId

            // The user-visible name of the channel: "Sample Email"
            val channelName: CharSequence = Content.mChannelName

            // The user-visible description of the channel: Sample Email Notifications
            val channelDescription: String = Content.mChannelDescription

            // Importance: Default Level (Notification Manager)
            val channelImportance: Int = Content.mChannelImportance

            // Vibrate Device
            val channelEnableVibrate: Boolean = Content.mChannelEnableVibrate

            // Visibility on the Lock Screen: Private
            val channelLockscreenVisibility: Int =
                Content.mChannelLockscreenVisibility

            // Initializes NotificationChannel, using:
            // 1. ID" channel_email_1
            // 2. Name: Sample Email
            // 3. Importance: Default Level for Notification Manager
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                channelImportance
            )

            // Description: Sample Email Notifications
            notificationChannel.description = channelDescription

            // Vibrate Device
            notificationChannel.enableVibration(channelEnableVibrate)

            // Visibility on the Lock Screen: Private
            notificationChannel.lockscreenVisibility = channelLockscreenVisibility

            // Adds NotificationChannel to system. Attempting to create an existing notification
            // channel with its original values performs no operation, so it's safe to perform the
            // below sequence.
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // notificationChannel = Channel ID, Name, Importance Level
            notificationManager.createNotificationChannel(notificationChannel)

            // The id of the channel.: channel_email_1
            return channelId

        } else {

            // Since "Inbox Style" is not available on the devices less then API Level 26, thus return NULL
            return ""

        }

    }

}