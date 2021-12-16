package com.example.notificationsdemo

import android.annotation.SuppressLint
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
import com.example.notificationsdemo.data.ContentBigImage
import com.example.notificationsdemo.data.ContentInboxStyle
import com.example.notificationsdemo.databinding.ActivityMainBinding
import com.example.notificationsdemo.utils.Notifications

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
        // Inbox Style Notification.
        binding.btnInboxStyle.setOnClickListener(this@MainActivity)

        // Big Image Notification
        binding.btnBigImageStyle.setOnClickListener(this@MainActivity)

    }

    // Assign a click event to button
    override fun onClick(v: View) {

        when (v.id) {

            // Call the generateInboxStyleNotification function.
            R.id.btn_inbox_style -> {
                generateInboxStyleNotification()
                return
            }

            // Assign a click event to button and call the generateInboxStyleNotification function.
            R.id.btn_big_image_style -> {
                generateBigPictureStyleNotification()
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
    @SuppressLint("UnspecifiedImmutableFlag")
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
            Notifications().createInboxStyleNotificationChannel(this)

        /**
         * Prepare inboxStyle Notification
         */

        // 2. Build the INBOX_STYLE.
        val inboxStyle = NotificationCompat.InboxStyle()

            // Title = "5 new emails from BlenderBros, Wingfox, DesignCourse +2"
            .setBigContentTitle(ContentInboxStyle.mBigContentTitle)

            // Summary = New email messages
            .setSummaryText(ContentInboxStyle.mSummaryText)

        // Add each summary line of the new emails, you can add up to 5.
        for (summary in ContentInboxStyle.mIndividualEmailSummary()) {

            // Separate individual lines from the ArrayList defined in the Content.kt
            inboxStyle.addLine(summary)

        }

        /**
         * We will need to create a "Pending Intent" using first defining the Intent()
         */

        // 3. Set up main Intent for notification that is the Activity that you want to launch when user tap on notification.
        val mainIntent = Intent(this, MainActivity::class.java)

        // Gets a PendingIntent containing the intent.
        val mainPendingIntent = PendingIntent.getActivity(
            this,
            0,
            mainIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        /**
         * 4. Build and issue the Notification.
         */

        // Because we want this to be a new notification (not updating a previous notification), we
        // create a new Builder. However, we don't need to update this notification later, so we
        // will not need to set a global builder for access to the notification later.
        val notificationCompatBuilder = NotificationCompat.Builder(

            // Get the Active Context
            applicationContext,

            // Defined in the Step #1
            notificationChannelId
        )


        /**
         * Configuration: Setting all properties of the Notification Builder
         */
        notificationCompatBuilder

            // Expanded State: Title + Content for API 16+ (> v4.1)
            .setStyle(inboxStyle)

            // Collapsed State: Title for API <16 (4.0 and below)
            .setContentTitle(ContentInboxStyle.mContentTitle)

            // Collapsed State: Content for API <24 (7.0 and below) devices and API 16+ (4.1 and after)
            .setContentText(ContentInboxStyle.mContentText)

            // Icon: Small
            .setSmallIcon(R.drawable.ic_stat_notification)

            // Icon: Large
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    resources,
                    R.drawable.ic_person
                )
            )

            // Gets a PendingIntent containing the intent.
            .setContentIntent(mainPendingIntent)

            // Apply default values (important for Wear 2.0 Notifications).
            .setDefaults(NotificationCompat.DEFAULT_ALL)

            // Set primary color (important for Wear 2.0 Notifications).
            .setColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.purple_500
                )
            )

            /**
             * Side Note:
             * Auto-bundling is enabled for 4 or more notifications on API 24+ (N+)
             * devices and all Wear devices. If you have more than one notification and
             * you prefer a different summary notification, set a group key and create a
             * summary notification via
             *      .setGroupSummary(true)
             *      .setGroup(GROUP_KEY_YOUR_NAME_HERE)
             */


            // mNumberOfNewEmails = 5
            // Sets large number at the right-hand side of the notification for API <24 devices.
            .setSubText(ContentInboxStyle.mNumberOfNewEmails.toString())

            // CATEGORY_EMAIL = "email" (Notification will be like an email)
            .setCategory(Notification.CATEGORY_EMAIL)

            // Sets priority for 25 and below: mPriority = NotificationCompat.PRIORITY_DEFAULT
            // For 26 and above, 'priority' is deprecated for 'importance' which is set in the NotificationChannel.
            // Caution:  The integers representing 'priority' are different from 'importance', so make sure you don't mix them.
            .setPriority(ContentInboxStyle.mPriority)

            // Sets lock-screen visibility for 25 and below. For 26 and above, lock screen
            // visibility is set in the NotificationChannel.
            .setVisibility(ContentInboxStyle.mChannelLockscreenVisibility)

        /**
         * Fallback Settings (In case if the device is in "Do Not Disturb" mode).
         */

        // If the phone is in "Do not disturb mode, the user will still be notified if the sender(s) is starred as a favorite.
        for (name in ContentInboxStyle.mEmailSenders()) {

            // TODO: Replace deprecated addPerson() method.
            notificationCompatBuilder.addPerson(name)

        }

        // Prepare (build) the recently defined notification
        val notification = notificationCompatBuilder.build()

        // Using the Notification Builder notify the user with:
        // 1. Notification ID
        // 2. Notification Builder
        mNotificationManagerCompat.notify(
            NOTIFICATION_ID,
            notification
        )
    }

    // Create a function to generate and launch the BigPictureStyle notification.
    /*
     * Generates a BIG_PICTURE_STYLE Notification that supports both phone/tablet and wear. For
     * devices on API level 16 (4.1.x - Jelly Bean) and after, displays BIG_PICTURE_STYLE.
     * Otherwise, displays a basic notification.
     *
     * This example Notification is a social post.
     */
    private fun generateBigPictureStyleNotification() {

        // Main steps for building a BIG_PICTURE_STYLE notification:
        //      0. Get your data
        //      1. Create/Retrieve Notification Channel for O and beyond devices (26+)
        //      2. Build the BIG_PICTURE_STYLE
        //      3. Set up main Intent for notification
        //      4. Set up RemoteInput, so users can input (keyboard and voice) from notification
        //      5. Build and issue the notification

        // 0. Get your data (everything unique per Notification).

        // 1. Create/Retrieve Notification Channel for O and beyond devices (26+).
        val notificationChannelId: String =
            Notifications().createBigPictureStyleNotificationChannel(this@MainActivity)

        // 2. Build the BIG_PICTURE_STYLE.
        val bigPictureStyle =
            NotificationCompat.BigPictureStyle() // Provides the bitmap for the BigPicture notification.
                .bigPicture(
                    BitmapFactory.decodeResource(
                        resources,
                        ContentBigImage.mBigImage
                    )
                ) // Overrides ContentTitle in the big form of the template.
                .setBigContentTitle(ContentBigImage.mBigContentTitle) // Summary line after the detail section in the big form of the template.
                .setSummaryText(ContentBigImage.mSummaryText)

        // 3. Set up main Intent for notification.
        val mainIntent = Intent(this, MainActivity::class.java)
        // Gets a PendingIntent containing the mainIntent.
        val mainPendingIntent = PendingIntent.getActivity(
            this,
            0,
            mainIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // 5. Build and issue the notification.

        // Because we want this to be a new notification (not updating a previous notification), we
        // create a new Builder. Later, we use the same global builder to get back the notification
        // we built here for a comment on the post.
        val notificationCompatBuilder = NotificationCompat.Builder(
            applicationContext, notificationChannelId
        )

        notificationCompatBuilder

            // BIG_PICTURE_STYLE sets title and content for API 16 (4.1 and after).
            .setStyle(bigPictureStyle)

            // Title for API <16 (4.0 and below) devices.
            .setContentTitle(ContentBigImage.mContentTitle)

            // Content for API <24 (7.0 and below) devices.
            .setContentText(ContentBigImage.mContentText)
            .setSmallIcon(R.drawable.ic_stat_notification)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    resources,
                    R.drawable.ic_person
                )
            )
            .setContentIntent(mainPendingIntent)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

            // Set primary color (important for Wear 2.0 Notifications).
            .setColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.purple_500
                )
            )

            // SIDE NOTE: Auto-bundling is enabled for 4 or more notifications on API 24+ (N+)
            // devices and all Wear devices. If you have more than one notification and
            // you prefer a different summary notification, set a group key and create a
            // summary notification via
            // .setGroupSummary(true)
            // .setGroup(GROUP_KEY_YOUR_NAME_HERE)
            .setSubText(1.toString())
            .setCategory(Notification.CATEGORY_SOCIAL)

            // Sets priority for 25 and below. For 26 and above, 'priority' is deprecated for
            // 'importance' which is set in the NotificationChannel. The integers representing
            // 'priority' are different from 'importance', so make sure you don't mix them.
            .setPriority(ContentBigImage.mPriority)

            // Sets lock-screen visibility for 25 and below. For 26 and above, lock screen
            // visibility is set in the NotificationChannel.
            .setVisibility(ContentBigImage.mChannelLockscreenVisibility)

        // If the phone is in "Do not disturb mode, the user will still be notified if
        // the sender(s) is starred as a favorite.
        for (name in ContentBigImage.mParticipants()) {
            notificationCompatBuilder.addPerson(name)
        }

        val notification = notificationCompatBuilder.build()

        // Notify the user using notification id and Notification builder with notification manager.
        mNotificationManagerCompat.notify(NOTIFICATION_ID, notification)
    }
}