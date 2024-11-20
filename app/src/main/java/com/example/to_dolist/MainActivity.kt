package com.example.to_dolist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class MainActivity : AppCompatActivity(), TaskAdapter.RecyclerViewEvent {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: TaskAdapter
    private lateinit var recyclerView: RecyclerView
    private val taskList = mutableListOf<Task>()

    private val addTaskLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Log.i("Activities", "MainActivity - Returned from TaskActivity")
            listenForTasks()
        }
    }

    private val editTaskLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val taskId = result.data?.getStringExtra("TASK_ID")
            if (taskId != null) {
                deleteTask(taskId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Check if user is authenticated
        if (auth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.tasks_home)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TaskAdapter(taskList, this)
        recyclerView.adapter = adapter

        // Set up buttons
        setupButtons()

        // Start listening for tasks
        listenForTasks()
    }

    private fun setupButtons() {
        val addButton: Button = findViewById(R.id.add_task_button)
        val signOutButton: Button = findViewById(R.id.signOutButton)

        addButton.setOnClickListener {
            Log.i("Buttons", "Add button pressed - MainActivity")
            val taskIntent = Intent(this, TaskActivity::class.java)
            addTaskLauncher.launch(taskIntent)
        }

        signOutButton.setOnClickListener {
            signOut()
        }
    }

    private fun listenForTasks() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            Log.d("MainActivity", "Listening for tasks for user: $userId")
            db.collection("tasks")
                .whereEqualTo("userId", userId)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.w("MainActivity", "Listen failed.", e)
                        return@addSnapshotListener
                    }

                    if (snapshot != null) {
                        val tasks = snapshot.documents.mapNotNull { doc ->
                            doc.toObject(Task::class.java)?.copy(id = doc.id)
                        }
                        Log.d("MainActivity", "Received ${tasks.size} tasks")
                        taskList.clear()
                        taskList.addAll(tasks)
                        adapter.notifyDataSetChanged()
                    }
                }
        }
    }

    private fun deleteTask(taskId: String) {
        db.collection("tasks").document(taskId)
            .delete()
            .addOnSuccessListener {
                Log.d("MainActivity", "Task successfully deleted")
                Toast.makeText(this, "Task completed!", Toast.LENGTH_SHORT).show()
                // Update the task list and notify the adapter
                taskList.removeIf { it.id == taskId }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Log.w("MainActivity", "Error deleting task", e)
                Toast.makeText(this, "Error completing task", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onItemClick(task: Task, position: Int) {
        Log.i("Buttons", "MainActivity - Task item $position pressed")
        val editIntent = Intent(this, EditTaskActivity::class.java)
        editIntent.putExtra("TASK_ID", task.id)
        editIntent.putExtra("TASK_TITLE", task.title)
        editTaskLauncher.launch(editIntent)
    }

    private fun signOut() {
        auth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}