package com.example.to_dolist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text
import kotlin.properties.Delegates

/**
 * Allows user to browse individual tasks and mark them as complete
 */
class EditTaskActivity : AppCompatActivity() {
    var tasks: ArrayList<String> = ArrayList()
    var index = -1
    var count = 0
    var title = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_task)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    @Override
    override fun onStart() {
        super.onStart()
        //Read forwarded data:
        index = intent.getIntExtra("INDEX", -1)
        count = intent.getIntExtra("COUNT",0)
        for (i: Int in 0..count) {
            tasks.add(intent.getStringExtra("TASK_$i").toString())
        }
        title = tasks[index]

        //Initialize TextView:
        val taskHeader: TextView = findViewById(R.id.edit_task_header)
        taskHeader.text = title

        //Initialize buttons:
        val completeButton: Button = findViewById(R.id.complete_button)
        val nextButton: Button = findViewById(R.id.next_button)
        val previousButton: Button = findViewById(R.id.previous_button)
        val cancelButton: Button = findViewById(R.id.cancel_complete_task_button)

        //Add listeners to the buttons
        completeButton.setOnClickListener{
            Log.i("Buttons","Mark as Complete button pressed - EditTaskActivity")
            val resultIntent = Intent()
            resultIntent.putExtra("INDEX",index)
            setResult(RESULT_OK, resultIntent)
            Log.i("Activities","EditTaskActivity - Close with Results: $title at index $index. $count tasks remain")
            finish()
        }
        nextButton.setOnClickListener{
            Log.i("Buttons","Next button pressed - EditTaskActivity")
            index = (index+1)%count
            title = tasks[index]
            val relaunchIntent = Intent(this, EditTaskActivity::class.java)
            relaunchIntent.putExtra("INDEX",index)
            relaunchIntent.putExtra("COUNT",count)
            for (i:Int in 0..count) {
                relaunchIntent.putExtra("TASK_$i",tasks[i])
            }
            Log.i("Activities","EditTaskActivity - Change to next task: $title")
            relauncher.launch(relaunchIntent)
        }
        previousButton.setOnClickListener{
            Log.i("Buttons","Previous button pressed - EditTaskActivity")
            index = (index-1)%count
            title = tasks[index]
            val relaunchIntent = Intent(this, EditTaskActivity::class.java)
            relaunchIntent.putExtra("INDEX",index)
            relaunchIntent.putExtra("COUNT",count)
            for (i:Int in 0..count) {
                relaunchIntent.putExtra("TASK_$i",tasks[i])
            }
            Log.i("Activities","EditTaskActivity - Change to previous task: $title")
            relauncher.launch(relaunchIntent)
        }
        cancelButton.setOnClickListener{
            Log.i("Buttons","Cancel button pressed - EditTaskActivity")
            Log.i("Activities","EditTaskActivity - Close without Result")
            finish()
        }
    }

    @Override
    override fun onResume() {
        Log.i("Activities", "EditTaskActivity - onResume called")
        super.onResume()

        title = tasks[index]

        //Initialize TextView:
        val taskHeader: TextView = findViewById(R.id.edit_task_header)
        taskHeader.text = title
    }

    @Override
    override fun onPause() {
        Log.i("Activities", "EditTaskActivity - onPause called")
        super.onPause()
    }

    @Override
    override fun onStop() {
        Log.i("Activities", "EditTaskActivity - onStop called")
        super.onStop()
    }

    @Override
    override fun onDestroy() {
        Log.i("Activities", "EditTaskActivity - onDestroy called")
        super.onDestroy()
    }

    @Override
    override fun onSaveInstanceState(outState: Bundle) {
        Log.i("Activities", "EditTaskActivity - onSaveInstanceState called")
        outState.run {
            putString("TITLE", title)
            putInt("COUNT",count)
            putInt("INDEX",index)
        }
        super.onSaveInstanceState(outState)
    }
    companion object {
        val TITLE = "TITLE"
        val COUNT = "COUNT"
        val INDEX = "INDEX"
    }

    @Override
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.i("Activities", "EditTaskActivity - onRestoreInstanceState called")
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState.run {
            title = getString("STATE_RESULT","")
            count = getInt("COUNT")
            index = getInt("INDEX")
        }
    }

    //Launcher for navigating next and previous tasks
    private val relauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            //Handle the response from the EditTaskActivity
            val taskIndex = result.data?.getIntExtra("INDEX",-1)
            if (taskIndex in 0..6) {
                val resultIntent = Intent()
                resultIntent.putExtra("INDEX",taskIndex)
                setResult(RESULT_OK, resultIntent)
            }
            finish()
        }
    }
}