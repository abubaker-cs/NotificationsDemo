package com.example.notificationsdemo.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.notificationsdemo.data.ContentInboxStyle

// Create a class as Notification Util also create a function to create notification channel.
class Notifications {

    fun createInboxStyleNotificationChannel(context: Context): String {

        // Note: NotificationChannels are required for Notifications on O (API 26) and above.

        // 0 (API 26) - Released publicly as Android 8.0 in August 2017.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // The id of the channel.: channel_email_1
            val channelId: String = ContentInboxStyle.mChannelId

            // The user-visible name of the channel: "Sample Email"
            val channelName: CharSequence = ContentInboxStyle.mChannelName

            // The user-visible description of the channel: Sample Email Notifications
            val channelDescription: String = ContentInboxStyle.mChannelDescription

            // Importance: Default Level (Notification Manager)
            val channelImportance: Int = ContentInboxStyle.mChannelImportance

            // Vibrate Device
            val channelEnableVibrate: Boolean = ContentInboxStyle.mChannelEnableVibrate

            // Visibility on the Lock Screen: Private
            val channelLockscreenVisibility: Int =
                ContentInboxStyle.mChannelLockscreenVisibility

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


    // Create a function to create notification channel for BigPictureStyle.
    // We can use the same that we have created for InboxStyle but for learning purpose it is better to create separate.
    fun createBigPictureStyleNotificationChannel(context: Context): String {

        // NotificationChannels are required for Notifications on O (API 26) and above.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // The id of the channel.
            val channelId: String = ContentInboxStyle.mChannelId

            // The user-visible name of the channel.
            val channelName: CharSequence = ContentInboxStyle.mChannelName
            // The user-visible description of the channel.
            val channelDescription: String = ContentInboxStyle.mChannelDescription
            val channelImportance: Int = ContentInboxStyle.mChannelImportance
            val channelEnableVibrate: Boolean = ContentInboxStyle.mChannelEnableVibrate
            val channelLockscreenVisibility: Int = ContentInboxStyle.mChannelLockscreenVisibility

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