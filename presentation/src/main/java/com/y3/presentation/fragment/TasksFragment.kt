package com.y3.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.y3.common.Resource
import com.y3.domain.model.TaskDomainModel
import com.y3.presentation.R
import com.y3.presentation.databinding.FragmentTasksBinding
import com.y3.presentation.extensions.toast
import com.y3.presentation.fragment.adapters.TasksAdapter
import com.y3.presentation.model.TasksUiState
import com.y3.presentation.viewmodel.TasksViewModel
import com.yyy.noteapp.extensions.beGone
import com.yyy.noteapp.extensions.beVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TasksFragment : BaseBindingFragment<FragmentTasksBinding>() {

    private val tasksViewModel: TasksViewModel by viewModels()
    private var tasksAdapter: TasksAdapter? = null

    override fun inflateBinding(layoutInflater: LayoutInflater): FragmentTasksBinding {
        return FragmentTasksBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tasksToolbar.header.text = getString(R.string.tasks)
        setViewModels()
    }

    private fun setViewModels() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    tasksViewModel.tasks.collect{
                        when(it){
                            is TasksUiState.Idle -> {

                            }
                            TasksUiState.Failure -> {
                                toast("error")
                            }
                            TasksUiState.Loading -> {
                                binding.loadingBar.beVisible()
                            }
                            is TasksUiState.Success -> {
                                if (it.tasks.isEmpty()) {
                                    binding.infoText.beVisible()
                                }
                                showList()
                                tasksAdapter = TasksAdapter(it.tasks) {
                                    toast("navigation")
                                }
                                binding.tasksRv.adapter = tasksAdapter
                            }
                        }
                    }
                }
            }
        }
    }


    private fun showList() {
        binding.tasksRv.beVisible()
        binding.loadingBar.beGone()
    }

    private fun goneViews() {
        binding.loadingBar.beGone()
        binding.tasksRv.beGone()
    }

    override fun onDestroyView() {
        tasksAdapter = null
        super.onDestroyView()
    }

}