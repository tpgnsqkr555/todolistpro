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

    @Override
    override fun onStart() {
        Log.i("Activities","AboutActivity - onStart called")
        super.onStart()
    }

    @Override
    override fun onRestart() {
        Log.i("Activities","AboutActivity - onRestart called")
        super.onRestart()
    }

    @Override
    override fun onResume() {
        Log.i("Activities","AboutActivity - onResume called")
        super.onResume()
    }

    @Override
    override fun onPause() {
        Log.i("Activities","AboutActivity - onPause called")
        super.onPause()
    }

    @Override
    override fun onStop() {
        Log.i("Activities","AboutActivity - onStop called")
        super.onStop()
    }

    @Override
    override fun onDestroy() {
        Log.i("Activities","AboutActivity - onDestroy called")
        super.onDestroy()
    }
}