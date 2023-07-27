package com.y3.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.y3.presentation.viewmodel.TasksViewModel

abstract class BaseBindingFragment<T : ViewBinding> : Fragment() {

    protected val binding: T
        get() = _binding ?: throw IllegalStateException("Binding cannot be null")
    private var _binding: T? = null

    protected val viewModel:TasksViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateBinding(layoutInflater)
        return binding.root
    }

    abstract fun inflateBinding(layoutInflater: LayoutInflater): T

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}