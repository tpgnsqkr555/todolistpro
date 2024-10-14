package com.example.to_dolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter (private val dataSet: ArrayList<String>, private val listener: RecyclerViewEvent) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {interface RecyclerViewEvent {

        //Returns the clicked item position
        fun onItemClick(
            position: Int)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) , View.OnClickListener {
        val textView: TextView
        init {
            textView = view.findViewById(R.id.task_item)
            textView.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            listener.onItemClick(adapterPosition)
        }
    }

    @Override
    override fun onCreateViewHolder(
        viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.tasks_view_layout, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    override fun onBindViewHolder(viewHolder: ViewHolder,
                                  position: Int) {
        if (dataSet[position]!=null)
            viewHolder.textView.text = dataSet[position]
         else
            viewHolder.textView.text =  ""
    }
    //Returns the size of the dataset
    @Override
    override fun getItemCount() =
        if (dataSet.size>7) 7
        else dataSet.size
}