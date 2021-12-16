package com.example.notificationsdemo

import android.app.NotificationManager
import androidx.core.app.NotificationCompat

// Create an object and define the static data for Inbox Style that we will use while building notification.
object InboxStyleMockData {

    // Standard Notification values:
    // Title/Content for API <16 (4.0 and below) devices.
    const val mContentTitle = "5 new emails"
    const val mContentText = "from Jane, Jay, Alex +2 more"
    const val mNumberOfNewEmails = 5
    const val mPriority = NotificationCompat.PRIORITY_DEFAULT

    // Style notification values:
    const val mBigContentTitle = "5 new emails from Jane, Jay, Alex +2"
    const val mSummaryText = "New emails"

    fun mIndividualEmailSummary(): ArrayList<String> {
        // Add each summary line of the new emails, you can add up to 5.
        val list = ArrayList<String>()

        list.add("Jane Faab  -   Launch Party is here...")
        list.add("Jay Walker -   There's a turtle on the server!")
        list.add("Alex Chang -   Check this out...")
        list.add("Jane Johns -   Check in code?")
        list.add("John Smith -   Movies later....")

        return list
    }

    fun mParticipants(): ArrayList<String> {
        // If the phone is in "Do not disturb mode, the user will still be notified if
        // the user(s) is starred as a favorite.
        val list = ArrayList<String>()

        list.add("Jane Faab")
        list.add("Jay Walker")
        list.add("Alex Chang")
        list.add("Jane Johns")
        list.add("John Smith")

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