package com.example.to_dolist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * Receives data for a new task from the user and returns it to the MainActivity
 */
class TaskActivity : AppCompatActivity() {
    private var acceptableInput: Boolean = false
    private var result: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_task)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Instantiate Add Task Page Buttons:
        val saveButton: Button = findViewById(R.id.save_task_button)
        val cancelButton: Button = findViewById(R.id.cancel_edit_task_button)

        //Instantiate UserInput EditText:
        val newTaskName: EditText = findViewById(R.id.task_input)

        //Add listeners to the buttons that launch the related activities
        saveButton.setOnClickListener{
            Log.i("Buttons","Save button pressed - TaskActivity")
            result = newTaskName.text.toString()
            Log.i("Values","New Task: $result")
            acceptableInput = (result.length in 3..25)
            if (acceptableInput) {//If the input is acceptable, save the result to the resultIntent
                Log.i("Activities","TaskActivity - Close with Result: $result")
                val resultIntent = Intent()
                resultIntent.putExtra("RESULT",result)
                setResult(RESULT_OK, resultIntent)
                finish()
            } else Log.i("Activities","TaskActivity - Result not valid")
        }
        cancelButton.setOnClickListener{
            Log.i("Buttons","Cancel button pressed - TaskActivity")
            Log.i("Activities","TaskActivity - Close without Result")
            finish()
        }
    }
}