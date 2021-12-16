package com.example.notificationsdemo.data

import android.app.NotificationManager
import androidx.core.app.NotificationCompat

// "Sample Data" which can be used to display an Inbox Style Notification.
object ContentInboxStyle {

    /**
     * Title/Content
     * API < v16 (4.0 and below)
     */
    const val mContentTitle = "5 new emails"
    const val mContentText = "from BlenderBros, Wingfox, DesignCourse +2 more"
    const val mNumberOfNewEmails = 5
    const val mPriority = NotificationCompat.PRIORITY_DEFAULT

    /**
     * Style notification values
     * API v16+
     */
    const val mBigContentTitle = "5 new emails from BlenderBros, Wingfox, DesignCourse +2"
    const val mSummaryText = "New email messages"

    /**
     * Individual Email Summary
     */
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

    /**
     * What if the user has enabled "Do not Disturb" mode?
     */
    fun mEmailSenders(): ArrayList<String> {
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

    /**
     * API v26+ - Notification Channel Values
     */

    // Channel ID
    const val mChannelId = "channel_email_1"

    // The user-visible name of the channel.
    const val mChannelName = "Sample Email"

    // The user-visible description of the channel.
    const val mChannelDescription = "Sample Email Notifications"

    // Levels:
    // 1. Minimum level
    // 2. Low Level
    // 3. Default level
    const val mChannelImportance = NotificationManager.IMPORTANCE_DEFAULT

    // Vibrate Device
    const val mChannelEnableVibrate = true

    // Notification Visibility Level?
    // 1. Public
    // 2. Private
    // 3. Secrete
    const val mChannelLockscreenVisibility = NotificationCompat.VISIBILITY_PRIVATE
}