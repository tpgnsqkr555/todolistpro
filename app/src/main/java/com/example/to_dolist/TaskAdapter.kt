package com.example.to_dolist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private var tasks: List<Task> = listOf(),
    private val listener: RecyclerViewEvent
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    interface RecyclerViewEvent {
        fun onItemClick(task: Task, position: Int)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val textView: TextView = view.findViewById(R.id.task_item)
        val imageView: ImageView = view.findViewById(R.id.task_checkbox)

        init {
            imageView.setImageResource(R.drawable.to_do_list_app_4_checkbox)
            imageView.setOnClickListener(this)
            textView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(tasks[position], position)
            }
        }
    }

    fun updateTasks(newTasks: List<Task>) {
        Log.d("TaskAdapter", "Updating with ${newTasks.size} tasks")
        tasks = newTasks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.tasks_view_layout, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val task = tasks[position]
        Log.d("TaskAdapter", "Binding task: ${task.title} at position $position")
        viewHolder.textView.text = task.title
    }

    override fun getItemCount() = tasks.size
}