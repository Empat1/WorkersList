package com.example.workerslist.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.workerslist.databinding.ItemWorkerBinding
import com.example.workerslist.domain.Worker

class WorkerAdapter : ListAdapter<Worker, WorkerViewHolder>(WorkerDiffCallback) {

    var onWorkerItemClickListener: ((Worker) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkerViewHolder {
        val binding = ItemWorkerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WorkerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkerViewHolder, position: Int) {
        val worker = getItem(position)
        with(holder.binding){
            tvName.text = worker.fio
            tvWorkerRole.text = worker.jobRole
            tvWorkerBirthday.text = worker.birthday

            root.setOnClickListener {
                onWorkerItemClickListener?.invoke(worker)
            }
        }
    }
}