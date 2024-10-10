package com.example.to_dolist

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * Entry Activity for when the application is launched
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Instantiate Main Page Buttons:
        val addButton: Button = findViewById(R.id.add_task_button)
        val aboutButton: Button = findViewById(R.id.about_button)

        //Add listeners to the buttons that launch the related activities
        addButton.setOnClickListener{
            //TODO: launch the AddTaskActivity
        }
        aboutButton.setOnClickListener{
            //TODO: launch the AboutActivity
        }
    }
}