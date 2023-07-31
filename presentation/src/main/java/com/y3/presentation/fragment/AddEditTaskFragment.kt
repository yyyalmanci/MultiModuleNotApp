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
import com.y3.domain.model.TaskDomainModel
import com.y3.presentation.R
import com.y3.presentation.databinding.FragmentAddEditTaskBinding
import com.y3.presentation.extensions.toast
import com.y3.presentation.model.AddEditTaskUiState
import com.y3.presentation.viewmodel.TasksViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Date

@AndroidEntryPoint
class AddEditTaskFragment : BaseBindingFragment<FragmentAddEditTaskBinding>() {

    private val addEditTaskViewModel: TasksViewModel by viewModels()
    private val navArgs: AddEditTaskFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navArgs.taskModel?.let {
            addEditTaskViewModel.setTaskEntityItem(it, navArgs.action)
        }
        setViewModelObservers()
        setMenu()
        setView()
    }

    override fun inflateBinding(layoutInflater: LayoutInflater): FragmentAddEditTaskBinding {
        return FragmentAddEditTaskBinding.inflate(layoutInflater)
    }

    private fun setViewModelObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.taskEdit.collect {
                        when (it) {
                            is AddEditTaskUiState.Failure -> {
                                toast(getString(it.text))
                            }
                            AddEditTaskUiState.Idle -> {}
                            AddEditTaskUiState.Loading -> {
                                //TODO()
                            }
                            AddEditTaskUiState.SuccessAdd -> {
                                navigateBack()
                            }
                            AddEditTaskUiState.SuccessDelete -> {}
                        }
                    }
                }
                launch {
                    viewModel.taskDelete.collect {
                        when (it) {
                            is AddEditTaskUiState.Failure -> {

                            }
                            AddEditTaskUiState.Idle -> {}
                            AddEditTaskUiState.Loading -> {
                                //TODO()
                            }
                            AddEditTaskUiState.SuccessAdd -> {}
                            AddEditTaskUiState.SuccessDelete -> {
                                findNavController().navigate(AddEditTaskFragmentDirections.actionAddEditTaskFragmentToTasksFragment())
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setMenu() {
        binding.addTaskToolbar.toolbar.apply {
            inflateMenu(R.menu.add_edit_menu)
            menu.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            menu.getItem(1).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                navigateBack()
            }
            menu.findItem(R.id.delete).isVisible =
                addEditTaskViewModel.isEditMode(navArgs.action)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.delete -> {
                        addEditTaskViewModel.deleteTaskItem((navArgs.taskModel as TaskDomainModel).id)
                        true
                    }
                    R.id.save -> {
                        addEditTaskViewModel.insertTaskItem(getTaskEntity())
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }
    }


    private fun setView() {
        binding.apply {
            if (addEditTaskViewModel.isEditMode(navArgs.action)) {
                addTaskToolbar.toolbar.title = getString(R.string.edit_task)
                navArgs.taskModel?.let { taskItem ->
                    title.setText(taskItem.title)
                    description.setText(taskItem.description)
                    imageUrl.setText(taskItem.imageUrl)
                }
            } else {
                addTaskToolbar.toolbar.title = getString(R.string.add_task)
            }
        }
    }

    private fun navigateBack() {
        findNavController().popBackStack()
    }

    private fun getTaskEntity(): TaskDomainModel {
        return if (addEditTaskViewModel.isEditMode(navArgs.action)) {
            TaskDomainModel(
                title = binding.title.text.toString(),
                description = binding.description.text.toString(),
                tag = EDITED_TAG,
                id = addEditTaskViewModel.taskModel.id,
                imageUrl = binding.imageUrl.text.toString(),
                updatedAt = Date()
            )
        } else {
            TaskDomainModel(
                title = binding.title.text.toString(),
                description = binding.description.text.toString(),
                imageUrl = binding.imageUrl.text.toString(),
                updatedAt = Date()
            )
        }
    }

    companion object {
        const val EDITED_TAG = "edited"
    }
}