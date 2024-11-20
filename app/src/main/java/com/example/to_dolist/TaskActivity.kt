package com.example.to_dolist

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TaskActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var acceptableInput: Boolean = false
    private var result: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val saveButton: Button = findViewById(R.id.save_task_button)
        val cancelButton: Button = findViewById(R.id.cancel_edit_task_button)
        val newTaskName: EditText = findViewById(R.id.task_input)

        saveButton.setOnClickListener {
            result = newTaskName.text.toString()
            acceptableInput = (result.length in 3..25)

            if (acceptableInput) {
                val userId = auth.currentUser?.uid
                if (userId != null) {
                    val task = Task(
                        userId = userId,
                        title = result
                    )

                    Log.d("TaskActivity", "Adding new task: $result")
                    db.collection("tasks")
                        .add(task)
                        .addOnSuccessListener {
                            Log.d("TaskActivity", "Task added with ID: ${it.id}")
                            Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show()
                            setResult(RESULT_OK)
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Log.e("TaskActivity", "Error adding task", e)
                            Toast.makeText(this, "Error adding task: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            } else {
                Toast.makeText(this, "Task name must be between 3 and 25 characters", Toast.LENGTH_SHORT).show()
            }
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }
}