package com.csd3156.project1.database.time

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.csd3156.project1.R

class TimeListAdapter: ListAdapter<Time, TimeListAdapter.TimeViewHolder>(TimeComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.time_list, parent, false)
        return TimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.time.toString(), current.id.toString())
    }

    class TimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val timeItemView: TextView = itemView.findViewById(R.id.textViewTimeValue)
        private val idItemView: TextView = itemView.findViewById(R.id.textViewTimeIndex)

        @SuppressLint("SetTextI18n")
        fun bind(time: String?, id: String?) {
            timeItemView.text = time
            idItemView.text = "#$id"
        }
    }

    class TimeComparator: DiffUtil.ItemCallback<Time>() {

        override fun areItemsTheSame(oldItem: Time, newItem: Time): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: Time, newItem: Time): Boolean {
            return oldItem.time == newItem.time
        }
    }
}