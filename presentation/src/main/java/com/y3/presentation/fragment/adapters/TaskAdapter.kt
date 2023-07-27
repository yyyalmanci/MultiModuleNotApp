package com.y3.presentation.fragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.y3.domain.model.TaskDomainModel
import com.y3.presentation.databinding.ItemTaskBinding

class TasksAdapter(
    var data: List<TaskDomainModel>,
    private val onClick: (TaskDomainModel) -> Unit
) : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(
            binding
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) =
        holder.bind(data[position], onClick)

    class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: TaskDomainModel,
            onClick: (TaskDomainModel) -> Unit,
        ) {
            binding.apply {
                description.text = item.description
                title.text = item.title
                root.setOnClickListener {
                    onClick(item)
                }
            }
        }
    }
}