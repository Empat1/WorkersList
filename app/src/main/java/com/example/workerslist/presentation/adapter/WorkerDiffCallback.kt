package com.example.workerslist.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.workerslist.domain.WorkerModel

object WorkerDiffCallback : DiffUtil.ItemCallback<WorkerModel>(){
    override fun areItemsTheSame(oldItem: WorkerModel, newItem: WorkerModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: WorkerModel, newItem: WorkerModel): Boolean {
        return oldItem == newItem
    }
}