package com.example.workerslist.presentation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.workerslist.ApplicationApp
import com.example.workerslist.databinding.FragmentWorkerBinding
import com.example.workerslist.helper.DateHelper
import com.example.workerslist.domain.WorkerModel
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.slots.Slot
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import javax.inject.Inject

class WorkerFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as ApplicationApp).component
    }

    private var workMode = ""
    private var workerModel: WorkerModel? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: WorkerViewModel

    @Inject
    lateinit var dateMapper: DateHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)

        parseParams()
        val binding = FragmentWorkerBinding.inflate(inflater, container, false)
        initUI(binding)
        return binding.root
    }

    private fun initUI(binding: FragmentWorkerBinding) {
        viewModel = ViewModelProvider(this, viewModelFactory)[WorkerViewModel::class.java]
        setMaskForBirthday(binding.etBirthday)

        selectMode(binding)
    }

    private fun setMaskForBirthday(editText: EditText){
        val slots: Array<Slot> = UnderscoreDigitSlotsParser().parseSlots("__-__-____")
        val birthdateMask = MaskImpl.createTerminated(slots)
        val watcherDate: FormatWatcher = MaskFormatWatcher(birthdateMask)
        watcherDate.installOnAndFill(editText)
    }

    private fun selectMode(binding: FragmentWorkerBinding) {
        when (workMode) {
            ADD_WORK_MODE -> settingAddMode(binding)
            READ_WORK_MODE -> settingRemoveMode(binding)
        }
    }

    private fun settingAddMode(binding: FragmentWorkerBinding) {
        with(binding) {
            btnAction.text = "Сохранить"

            binding.btnAction.setOnClickListener {
                saveBtn(
                    etFio.text.toString(),
                    etRole.text.toString().trim(),
                    etBirthday.text.toString()
                )
            }
        }
    }

    private fun settingRemoveMode(binding: FragmentWorkerBinding) {
        binding.btnAction.text = "Удалить"
        workerModel?.let { setWorker(binding, it) }
        binding.btnAction.setOnClickListener {
            workerModel?.let { it1 -> viewModel.removeWorker(it1) }
            parentFragmentManager.popBackStack()
        }
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(ARG_WORK_MODE))
            throw RuntimeException("Param work mode is absent")
        val mode = args.getString(ARG_WORK_MODE)
        if (mode != ADD_WORK_MODE && mode != READ_WORK_MODE)
            throw RuntimeException("Unknown work mode $mode")
        workMode = mode

        if (workMode == READ_WORK_MODE) {
            if (!args.containsKey(ARG_WORKER))
                throw RuntimeException("Param worker is absent")
            workerModel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                args.getParcelable(ARG_WORKER, WorkerModel::class.java)
            else
                args.getParcelable(ARG_WORKER)

        }
    }

    private fun setWorker(binding: FragmentWorkerBinding, workerModel: WorkerModel) {
        binding.etFio.setText(workerModel.fio)
        binding.etBirthday.setText(workerModel.birthday)
        binding.etRole.setText(workerModel.jobRole)
    }

    private fun saveBtn(fio: String, role: String, birthday: String) {
        val workerModel = WorkerModel(fio = fio, birthday =  birthday, jobRole =  role)
        if (viewModel.correctWorker(workerModel)) {
            viewModel.addWorker(workerModel)
            parentFragmentManager.popBackStack()
        }else{
            Toast.makeText(context, "Данные указаны некорректно", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        private const val ARG_WORK_MODE = "work_mode"
        private const val ARG_WORKER = "worker"
        private const val ADD_WORK_MODE = "add_work_mode"
        private const val READ_WORK_MODE = "read_work_mode"

        @JvmStatic
        fun newInstanceAddWorker() =
            WorkerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_WORK_MODE, ADD_WORK_MODE)
                }
            }

        @JvmStatic
        fun newInstanceRemoveWorker(workerModel: WorkerModel) =
            WorkerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_WORK_MODE, READ_WORK_MODE)
                    putParcelable(ARG_WORKER, workerModel)
                }
            }
    }
}