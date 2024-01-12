package com.example.workerslist.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.workerslist.ApplicationApp
import com.example.workerslist.databinding.FragmentListWorkerBinding
import com.example.workerslist.presentation.adapter.WorkerAdapter
import javax.inject.Inject

class ListWorkersFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as ApplicationApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ListWorkerViewModel
    private lateinit var adapter: WorkerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        val binding = FragmentListWorkerBinding.inflate(inflater, container, false)
        initUI(binding)
        return binding.root
    }

    private fun initUI(binding: FragmentListWorkerBinding) {
        viewModel = ViewModelProvider(this, viewModelFactory)[ListWorkerViewModel::class.java]
        settingWorkerAdapter(binding.rvWorkerList)

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.btnSave.isEnabled = it
            binding.buttonAddWorker.isEnabled = it
        }

        binding.buttonAddWorker.setOnClickListener {
            nextFragment(WorkerFragment.newInstanceAddWorker())
        }

        binding.btnSave.setOnClickListener {
            viewModel.save()
        }
    }

    private fun settingWorkerAdapter(recyclerView: RecyclerView) {
        adapter = WorkerAdapter()
        adapter.onWorkerItemClickListener = {
            nextFragment(WorkerFragment.newInstanceRemoveWorker(it))
        }
        recyclerView.adapter = adapter

        viewModel.getWorker().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun nextFragment(fragment: Fragment) {
        (requireActivity() as MainActivity)
            .launchNextFragment(fragment)
    }
}