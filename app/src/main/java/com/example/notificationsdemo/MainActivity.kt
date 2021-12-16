package com.example.notificationsdemo

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.notificationsdemo.data.Content
import com.example.notificationsdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    // Define the Notification Id and Create an instance of Notification Manager.
    private val NOTIFICATION_ID = 888
    private lateinit var mNotificationManagerCompat: NotificationManagerCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the notification Manager.
        mNotificationManagerCompat = NotificationManagerCompat.from(this@MainActivity)

        // Create an instance of Button and Assign the click event.
        binding.btnInboxStyle.setOnClickListener(this@MainActivity)

    }

    override fun onClick(v: View) {
        // Assign a click event to button and call the generateInboxStyleNotification function.
        when (v.id) {

            R.id.btn_inbox_style -> {
                generateInboxStyleNotification()
                return
            }
        }

    }

    /*
     * Generates a INBOX_STYLE Notification that supports both phone/tablet and wear.
     *
     * API < v16  - Basic Notification.
     * API > v16+ - Inbox style
     *
     */
    private fun generateInboxStyleNotification() {

        // Build a INBOX_STYLE notification as below:
        // Main steps for building a INBOX_STYLE notification:
        //      0. Get your data
        //      1. Create/Retrieve Notification Channel for O and beyond devices (26+)
        //      2. Build the INBOX_STYLE
        //      3. Set up main Intent for notification
        //      4. Build and issue the notification

        // 1. Create/Retrieve Notification Channel for O and beyond devices (26+).
        val notificationChannelId: String =
            NotificationUtil().createInboxStyleNotificationChannel(this)

        // 2. Build the INBOX_STYLE.
        val inboxStyle =
            NotificationCompat.InboxStyle() // This title is slightly different than regular title, since I know INBOX_STYLE is
                // available.
                .setBigContentTitle(Content.mBigContentTitle)
                .setSummaryText(Content.mSummaryText)

        // Add each summary line of the new emails, you can add up to 5.
        for (summary in Content.mIndividualEmailSummary()) {
            inboxStyle.addLine(summary)
        }

        // 3. Set up main Intent for notification that is the Activity that you want to launch when user tap on notification.
        val mainIntent = Intent(this, MainActivity::class.java)
        // Gets a PendingIntent containing the intent.
        val mainPendingIntent = PendingIntent.getActivity(
            this,
            0,
            mainIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // 4. Build and issue the notification.
        // Because we want this to be a new notification (not updating a previous notification), we
        // create a new Builder. However, we don't need to update this notification later, so we
        // will not need to set a global builder for access to the notification later.
        val notificationCompatBuilder = NotificationCompat.Builder(
            applicationContext,
            notificationChannelId
        )

        notificationCompatBuilder // INBOX_STYLE sets title and content for API 16+ (4.1 and after) when the
            // notification is expanded.
            .setStyle(inboxStyle) // Title for API <16 (4.0 and below) devices and API 16+ (4.1 and after) when the
            // notification is collapsed.
            .setContentTitle(Content.mContentTitle) // Content for API <24 (7.0 and below) devices and API 16+ (4.1 and after) when the
            // notification is collapsed.
            .setContentText(Content.mContentText)
            .setSmallIcon(R.drawable.ic_stat_notification)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    resources,
                    R.drawable.ic_person
                )
            )
            .setContentIntent(mainPendingIntent)
            .setDefaults(NotificationCompat.DEFAULT_ALL) // Set primary color (important for Wear 2.0 Notifications).
            .setColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.purple_500
                )
            ) // SIDE NOTE: Auto-bundling is enabled for 4 or more notifications on API 24+ (N+)
            // devices and all Wear devices. If you have more than one notification and
            // you prefer a different summary notification, set a group key and create a
            // summary notification via
            // .setGroupSummary(true)
            // .setGroup(GROUP_KEY_YOUR_NAME_HERE)
            // Sets large number at the right-hand side of the notification for API <24 devices.
            .setSubText(Content.mNumberOfNewEmails.toString())
            .setCategory(Notification.CATEGORY_EMAIL) // Sets priority for 25 and below. For 26 and above, 'priority' is deprecated for
            // 'importance' which is set in the NotificationChannel. The integers representing
            // 'priority' are different from 'importance', so make sure you don't mix them.
            .setPriority(Content.mPriority) // Sets lock-screen visibility for 25 and below. For 26 and above, lock screen
            // visibility is set in the NotificationChannel.
            .setVisibility(Content.mChannelLockscreenVisibility)

        // If the phone is in "Do not disturb mode, the user will still be notified if
        // the sender(s) is starred as a favorite.
        for (name in Content.mParticipants()) {
            notificationCompatBuilder.addPerson(name)
        }
        val notification = notificationCompatBuilder.build()

        // Notify to user using the Notification Id and Notification Builder with Notification Manager.
        mNotificationManagerCompat.notify(NOTIFICATION_ID, notification)
    }
}