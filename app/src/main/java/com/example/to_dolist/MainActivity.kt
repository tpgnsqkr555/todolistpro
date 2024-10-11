package com.example.to_dolist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Entry Activity for when the application is launched
 */
class MainActivity : AppCompatActivity(), TaskAdapter.RecyclerViewEvent {
    //Initialize the database and the Adapter
    val tasks: Array<String?> = arrayOfNulls(7)
    val taskAdapter = TaskAdapter(tasks, this)
    var index = 0

    //Launcher for TaskActivity results
    private val addTaskLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            //Handle the response from the TaskActivity
            var received_result = result.data?.getStringExtra("RESULT")
            Log.i("Activities", "MainActivity - Received result: $received_result from TaskActivity")
            tasks[index] = received_result
            index++
        }
    }
    override fun onItemClick(position: Int) {
        Log.i("Buttons", "MainActivity - Task item $position pressed")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Initialize the RecyclerView:
        val recyclerView: RecyclerView = findViewById(R.id.tasks_home)
        recyclerView.layoutManager = LinearLayoutManager(this) //Set the layout manager type
        recyclerView.adapter = taskAdapter //Set the Adapter

        //Instantiate Main Page Buttons:
        val addButton: Button = findViewById(R.id.add_task_button)
        val aboutButton: Button = findViewById(R.id.about_button)

        //Add listeners to the buttons that launch the related activities
        addButton.setOnClickListener{
            Log.i("Buttons","Add button pressed - MainActivity")
            val taskIntent = Intent(this, TaskActivity::class.java)
            addTaskLauncher.launch(taskIntent)
        }
        aboutButton.setOnClickListener{
            Log.i("Buttons","About button pressed - MainActivity")
            val aboutIntent = Intent(this, AboutActivity::class.java)
            startActivity(aboutIntent)
        }
    }
}