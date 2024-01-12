package com.example.workerslist.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.workerslist.domain.Worker

object WorkerDiffCallback : DiffUtil.ItemCallback<Worker>(){
    override fun areItemsTheSame(oldItem: Worker, newItem: Worker): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Worker, newItem: Worker): Boolean {
        return oldItem == newItem
    }
}