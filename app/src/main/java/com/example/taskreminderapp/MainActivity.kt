package com.example.taskreminderapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val taskTitle = findViewById<EditText>(R.id.editTextTaskTitle)
        val taskDescription = findViewById<EditText>(R.id.editTextTaskDescription)
        val buttonCreateTask = findViewById<Button>(R.id.buttonCreateTask)

        buttonCreateTask.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("TASK_TITLE", taskTitle.text.toString())
            intent.putExtra("TASK_DESCRIPTION", taskDescription.text.toString())
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_main_activity -> {
                // Already on MainActivity, no action needed
                true
            }
            R.id.action_detail_activity -> {
                val intent = Intent(this, DetailActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
