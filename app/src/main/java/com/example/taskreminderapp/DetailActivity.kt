package com.example.taskreminderapp

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import android.widget.TextView
import android.widget.Button

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val taskTitle = intent.getStringExtra("TASK_TITLE")
        val taskDescription = intent.getStringExtra("TASK_DESCRIPTION")

        val textViewTaskTitle = findViewById<TextView>(R.id.textViewTaskTitle)
        val textViewTaskDescription = findViewById<TextView>(R.id.textViewTaskDescription)
        val buttonSetReminder = findViewById<Button>(R.id.buttonSetReminder)

        textViewTaskTitle.text = taskTitle
        textViewTaskDescription.text = taskDescription

        buttonSetReminder.setOnClickListener {
            showNotification(taskTitle, taskDescription)
        }

        // Load the fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, TaskInfoFragment())
            .commit()
    }

    private fun showNotification(taskTitle: String?, taskDescription: String?) {
        val channelId = "task_reminder_channel"
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Task Reminders", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Reminder: $taskTitle")
            .setContentText(taskDescription)
            .setSmallIcon(R.drawable.baseline_circle_notifications_24) // Replace with your icon
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL) // Adds sound and vibration
            .build()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU || checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            Log.d("NotificationTest", "Notification is being shown")
            notificationManager.notify(1, notification)
        } else {
            Log.d("NotificationTest", "Requesting notification permission")
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1001)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_main_activity -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_detail_activity -> {
                // Already on DetailActivity, no action needed
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
