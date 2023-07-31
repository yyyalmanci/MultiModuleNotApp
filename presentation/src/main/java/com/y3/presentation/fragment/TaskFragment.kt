package com.y3.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.CircleCropTransformation
import com.y3.domain.model.TaskDomainModel
import com.y3.presentation.R
import com.y3.presentation.databinding.FragmentTaskBinding
import com.y3.presentation.model.TaskUiState
import com.y3.presentation.model.enums.ActionType
import com.y3.presentation.viewmodel.TasksViewModel
import com.yyy.noteapp.extensions.beGone
import com.yyy.noteapp.extensions.beVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TaskFragment : BaseBindingFragment<FragmentTaskBinding>() {

    private val taskViewModel: TasksViewModel by viewModels()
    private val navArgs: TaskFragmentArgs by navArgs()

    override fun inflateBinding(layoutInflater: LayoutInflater): FragmentTaskBinding {
        return FragmentTaskBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskViewModel.loadTask(navArgs.taskId)
        setViewModels()
    }

    private fun setViewModels() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.task.collect {
                        when (it) {
                            TaskUiState.Failure -> {
                                binding.loadingBar.beGone()
                            }
                            TaskUiState.Idle -> {
                                binding.loadingBar.beGone()
                            }
                            TaskUiState.Loading -> {
                                binding.loadingBar.beVisible()
                            }
                            is TaskUiState.Success -> {
                                binding.loadingBar.beGone()
                                setView(it.task)
                            }
                        }
                    }

                }
            }
        }
    }

    private fun setView(taskModel: TaskDomainModel) {
        binding.apply {
            taskToolbar.toolbar.title = getString(R.string.task_detail)
            setImage(taskModel.imageUrl)
            taskToolbar.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
            taskToolbar.toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            title.text = taskModel.title
            description.text = taskModel.description
            date.text = taskModel.updatedAt.toString()
            edit.setOnClickListener {
                findNavController().navigate(
                    TaskFragmentDirections.actionTaskFragmentToAddEditTaskFragment(
                        ActionType.EDIT,
                        taskModel,
                    )
                )
            }
        }
    }

    private fun setImage(url: String) {
        binding.image.load(url) {
            error(R.drawable.ic_image_not_found)
            crossfade(true)
            placeholder(R.drawable.ic_image_not_found)
            transformations(CircleCropTransformation())
        }
    }
}