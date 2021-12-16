package com.example.notificationsdemo

import android.app.NotificationManager
import androidx.core.app.NotificationCompat

// "Sample Data" which can be used to display an Inbox Style Notification.
object InboxStyleMockData {

    // Standard Notification values:
    // Title/Content for API <16 (4.0 and below) devices.
    const val mContentTitle = "5 new emails"
    const val mContentText = "from BlenderBros, Wingfox, DesignCourse +2 more"
    const val mNumberOfNewEmails = 5
    const val mPriority = NotificationCompat.PRIORITY_DEFAULT

    // Style notification values:
    const val mBigContentTitle = "5 new emails from BlenderBros, Wingfox, DesignCourse +2"
    const val mSummaryText = "New email messages"

    fun mIndividualEmailSummary(): ArrayList<String> {

        // Add each summary line of the new emails, you can add up to 5.
        val list = ArrayList<String>()

        list.add("Blender Bros  -   Better Portfolio Renders...")
        list.add("Design Course -   Official course launch is 18 days away")
        list.add("ArtStation    -   New Sign-in to ArtStation...")
        list.add("Wingfox       -   2 Blender Bundle Courses for as...")
        list.add("Bravo Team    -   New Like & Favourite buttons....")

        return list
    }

    fun mParticipants(): ArrayList<String> {
        // If the phone is in "Do not disturb mode, the user will still be notified if
        // the user(s) is starred as a favorite.
        val list = ArrayList<String>()

        list.add("Blender Bros")
        list.add("Design Course")
        list.add("ArtStation")
        list.add("Wingfox")
        list.add("Bravo Team")

        return list
    }

    // Notification channel values (for devices targeting 26 and above):
    const val mChannelId = "channel_email_1"

    // The user-visible name of the channel.
    const val mChannelName = "Sample Email"

    // The user-visible description of the channel.
    const val mChannelDescription = "Sample Email Notifications"
    const val mChannelImportance = NotificationManager.IMPORTANCE_DEFAULT
    const val mChannelEnableVibrate = true
    const val mChannelLockscreenVisibility = NotificationCompat.VISIBILITY_PRIVATE
}