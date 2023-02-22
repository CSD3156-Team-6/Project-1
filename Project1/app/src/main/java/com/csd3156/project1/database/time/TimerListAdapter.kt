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

class TimerListAdapter: ListAdapter<Timer, TimerListAdapter.TimerViewHolder>(TimerComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.time_list, parent, false)
        return TimerViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimerViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.timer.toString(), current.id.toString())
    }

    class TimerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val timeItemView: TextView = itemView.findViewById(R.id.textViewTimeValue)
        private val idItemView: TextView = itemView.findViewById(R.id.textViewTimeIndex)

        @SuppressLint("SetTextI18n")
        fun bind(timer: String?, id: String?) {
            timeItemView.text = timer
            idItemView.text = "#$id"
        }
    }

    class TimerComparator: DiffUtil.ItemCallback<Timer>() {

        override fun areItemsTheSame(oldItem: Timer, newItem: Timer): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: Timer, newItem: Timer): Boolean {
            return oldItem.timer == newItem.timer
        }
    }
}