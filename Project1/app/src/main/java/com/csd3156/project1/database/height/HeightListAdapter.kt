package com.csd3156.project1.database.height

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.csd3156.project1.R

class HeightListAdapter: ListAdapter<Height, HeightListAdapter.HeightViewHolder>(HeightComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeightViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.height_list, parent, false)
        return HeightViewHolder(view)
    }

    override fun onBindViewHolder(holder: HeightViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.height.toString(), current.id.toString())
    }

    class HeightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val heightItemView: TextView = itemView.findViewById(R.id.textViewHeightValue)
        private val idItemView: TextView = itemView.findViewById(R.id.textViewHeightIndex)

        @SuppressLint("SetTextI18n")
        fun bind(height: String?, id: String?) {
            heightItemView.text = height
            idItemView.text = "#$id"
        }
    }

    class HeightComparator: DiffUtil.ItemCallback<Height>() {

        override fun areItemsTheSame(oldItem: Height, newItem: Height): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: Height, newItem: Height): Boolean {
            return oldItem.height == newItem.height
        }
    }
}