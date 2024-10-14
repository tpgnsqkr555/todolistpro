package com.example.to_dolist

import android.app.Activity
import android.content.Context
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
    var tasks: ArrayList<String> = ArrayList()
    var count = 0

    //Launcher for TaskActivity results
    private val addTaskLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            //Handle the response from the TaskActivity
            var newTask = result.data?.getStringExtra("NEW_TASK")
            Log.i("Activities", "MainActivity - Received result: $newTask from TaskActivity")
            if (newTask != null && count < 7) {
                tasks.add(newTask)
                count++
            }
        }
    }
    //Launcher for EditTaskActivity results
    private val editTaskLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            //Handle the response from the EditTaskActivity
            val taskIndex = result.data?.getIntExtra("INDEX",-1)
            if (taskIndex in 0..<count) {
                Log.i("Activities", "MainActivity - Removing task $taskIndex from EditTaskActivity")
                tasks.removeAt(taskIndex!!)
                count--
            }
        }
    }

    /**
     * Description: Handles user interaction when clicking a RecyclerView list item
     * @param position is the index of the item being clicked
     */
    @Override
    override fun onItemClick(position: Int) {
        Log.i("Buttons", "MainActivity - Task item $position pressed")
        val editIntent = Intent(this, EditTaskActivity::class.java)
        editIntent.putExtra("INDEX",position)
        editIntent.putExtra("COUNT",count)
        for (i:Int in 0..<count) {
            editIntent.putExtra("TASK_$i",tasks[i])
        }
        editTaskLauncher.launch(editIntent)
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("Activities", "MainActivity - onCreate called")
        Log.i("Values", "MainActivity - index is $count")
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
        recyclerView.adapter = TaskAdapter(tasks, this) //Set the Adapter

        //Instantiate Main Page Buttons:
        val addButton: Button = findViewById(R.id.add_task_button)
        val aboutButton: Button = findViewById(R.id.about_button)

        //Add listeners to the buttons that launch the related activities

        //Add Listener:
        if (count<7) {
            //Add listener to the button that launch the related activity
            addButton.setOnClickListener{
                Log.i("Buttons", "Add button pressed - MainActivity")
                val taskIntent = Intent(this, TaskActivity::class.java)
                addTaskLauncher.launch(taskIntent)
            }
        } else {
            addButton.text = getString(R.string.too_much_work)
            //Add listener to the button that logs the input
            addButton.setOnClickListener {
                Log.i("Buttons", "Add button pressed, but too many tasks - MainActivity")
            }
        }
        aboutButton.setOnClickListener{
            Log.i("Buttons","About button pressed - MainActivity")
            val aboutIntent = Intent(this, AboutActivity::class.java)
            startActivity(aboutIntent)
        }
        //Save preferences
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        count = sharedPref.getString("INDEX", "0").toString().toInt()
        for (i:Int in 0..<count) {
            if (sharedPref.getString("TASK $i", "Issue").toString() != "Issue") {
                tasks.add(sharedPref.getString("TASK $i", "Issue...").toString())
            }
        }
    }

    @Override
    override fun onStart() {
        super.onStart()
        Log.i("Activities", "MainActivity - onStart called")
        Log.i("Values", "MainActivity - index is $count")

        //Initialize the RecyclerView:
        val recyclerView: RecyclerView = findViewById(R.id.tasks_home)
        recyclerView.layoutManager = LinearLayoutManager(this) //Set the layout manager type
        recyclerView.adapter = TaskAdapter(tasks, this) //Set the Adapter

        //Instantiate Main Page Buttons:
        val addButton: Button = findViewById(R.id.add_task_button)
        val aboutButton: Button = findViewById(R.id.about_button)

        //Add listeners to the buttons that launch the related activities

        //Add Listener:
        if (count<7) {
            //Add listener to the button that launch the related activity
            addButton.setOnClickListener{
                Log.i("Buttons", "Add button pressed - MainActivity")
                val taskIntent = Intent(this, TaskActivity::class.java)
                addTaskLauncher.launch(taskIntent)
            }
        } else {
            addButton.text = getString(R.string.too_much_work)
            //Add listener to the button that logs the input
            addButton.setOnClickListener {
                Log.i("Buttons", "Add button pressed, but too many tasks - MainActivity")
            }
        }
    }

    @Override
    override fun onRestart() {
        Log.i("Activities", "MainActivity - onRestart called")
        Log.i("Values", "MainActivity - index is $count")
        super.onRestart()

        //Initialize the RecyclerView:
        val recyclerView: RecyclerView = findViewById(R.id.tasks_home)
        recyclerView.layoutManager = LinearLayoutManager(this) //Set the layout manager type
        recyclerView.adapter = TaskAdapter(tasks, this) //Set the Adapter

        val addButton: Button = findViewById(R.id.add_task_button)

        if (count<7) {
            //Add listener to the button that launch the related activity
            addButton.setOnClickListener{
                Log.i("Buttons", "Add button pressed - MainActivity")
                val taskIntent = Intent(this, TaskActivity::class.java)
                addTaskLauncher.launch(taskIntent)
            }
        } else {
            addButton.text = getString(R.string.too_much_work)
            //Add listener to the button that logs the input
            addButton.setOnClickListener {
                Log.i("Buttons", "Add button pressed, but too many tasks - MainActivity")
            }
        }
    }

    @Override
    override fun onResume() {
        Log.i("Activities", "MainActivity - onResume called")
        Log.i("Values", "MainActivity - index is $count")
        super.onResume()

        //Initialize the RecyclerView:
        val recyclerView: RecyclerView = findViewById(R.id.tasks_home)
        recyclerView.layoutManager = LinearLayoutManager(this) //Set the layout manager type
        recyclerView.adapter = TaskAdapter(tasks, this) //Set the Adapter
    }

    @Override
    override fun onPause() {
        Log.i("Activities", "MainActivity - onPause called")
        Log.i("Values", "MainActivity - index is $count")
        super.onPause()
    }

    @Override
    override fun onStop() {
        super.onStop()
        Log.i("Activities", "MainActivity - onStop called")
        Log.i("Values", "MainActivity - index is $count")
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("INDEX","$count")
            if (count>0) {
                for (i: Int in 0..<count) {
                    putString("TASK $i", tasks[i])
                }
                apply()
            }
        }
    }

    @Override
    override fun onDestroy() {
        Log.i("Activities", "MainActivity - onDestroy called")
        super.onDestroy()
    }

    /**
     * Description:
     */
    @Override
    override fun onSaveInstanceState(outState: Bundle) {
        Log.i("Activities", "MainActivity - onSaveInstanceState called")
        Log.i("Values", "MainActivity - index is $count")
        // Save the user's current tasks state
        if (count>0) {
            outState.run {
                for (i: Int in 0..<count) {
                    putString("TASK_$i", tasks[i])
                }
                putString("INDEX", "$count")
            }
        }
        super.onSaveInstanceState(outState)
    }
    companion object {
        val INDEX = "INDEX"
        val TASK_0 = "TASK_0"
        val TASK_1 = "TASK_1"
        val TASK_2 = "TASK_2"
        val TASK_3 = "TASK_3"
        val TASK_4 = "TASK_4"
        val TASK_5 = "TASK_5"
        val TASK_6 = "TASK_6"
    }

    /**
     * Description:
     */
    @Override
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.i("Activities", "MainActivity - onRestoreInstanceState called")
        Log.i("Values", "MainActivity - index is $count")
        super.onRestoreInstanceState(savedInstanceState)
        // Restore tasks from saved instance.
        savedInstanceState.run {
            count = getString("INDEX","0").toString().toInt()
            if (count>0) {
                for (i:Int in 0..<count) {
                    tasks.add(getString("TASK_$i","").toString())
                }
            }
        }
    }
}