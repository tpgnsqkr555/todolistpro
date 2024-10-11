package com.example.to_dolist

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * About activity that displays information about the Developers
 */
class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_about)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Instantiate About page buttons:
        val backButton: Button = findViewById(R.id.about_back_button)

        //Add listeners to the buttons that launch the related activities
        backButton.setOnClickListener{ //Closes the Activity
            Log.i("Buttons","About: Back Button pressed - Closing Activity")
            finish()
        }
    }
}