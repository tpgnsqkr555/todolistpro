package com.example.to_dolist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class EditTaskActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var taskId: String = ""
    private var taskTitle: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Get task details from intent
        taskId = intent.getStringExtra("TASK_ID") ?: ""
        taskTitle = intent.getStringExtra("TASK_TITLE") ?: ""

        Log.d("EditTaskActivity", "Editing task: $taskTitle with ID: $taskId")

        // Initialize TextView
        val taskHeader: TextView = findViewById(R.id.edit_task_header)
        taskHeader.text = taskTitle

        // Initialize buttons
        val completeButton: Button = findViewById(R.id.complete_button)
        val cancelButton: Button = findViewById(R.id.cancel_complete_task_button)

        // Set up button listeners
        completeButton.setOnClickListener {
            Log.i("Buttons", "Mark as Complete button pressed - EditTaskActivity")
            completeTask()
        }

        cancelButton.setOnClickListener {
            Log.i("Buttons", "Cancel button pressed - EditTaskActivity")
            finish()
        }
    }

    private fun completeTask() {
        if (taskId.isNotEmpty()) {
            db.collection("tasks").document(taskId)
                .delete()
                .addOnSuccessListener {
                    Log.d("EditTaskActivity", "Task successfully completed and removed")
                    val resultIntent = Intent()
                    resultIntent.putExtra("TASK_ID", taskId)
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }
                .addOnFailureListener { e ->
                    Log.w("EditTaskActivity", "Error completing task", e)
                    Toast.makeText(this, "Error completing task: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run {
            putString("TASK_ID", taskId)
            putString("TASK_TITLE", taskTitle)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.run {
            taskId = getString("TASK_ID", "")
            taskTitle = getString("TASK_TITLE", "")
        }
    }
}