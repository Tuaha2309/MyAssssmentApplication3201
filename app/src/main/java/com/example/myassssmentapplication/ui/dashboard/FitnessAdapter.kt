package com.example.myassssmentapplication.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myassssmentapplication.databinding.ItemFitnessBinding
import com.example.myassssmentapplication.domain.model.FitnessEntity

class FitnessAdapter(
    private val onItemClick: (FitnessEntity) -> Unit
) : ListAdapter<FitnessEntity, FitnessAdapter.FitnessViewHolder>(FitnessDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FitnessViewHolder {
        val binding = ItemFitnessBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FitnessViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: FitnessViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class FitnessViewHolder(
        private val binding: ItemFitnessBinding,
        private val onItemClick: (FitnessEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: FitnessEntity) {
            binding.apply {
                exerciseName.text = entity.exerciseName
                muscleGroup.text = entity.muscleGroup
                difficulty.text = entity.difficulty
                equipment.text = entity.equipment
                caloriesText.text = "${entity.caloriesBurnedPerHour} kcal/hr"
                
                root.setOnClickListener {
                    onItemClick(entity)
                }
            }
        }
    }

    private class FitnessDiffCallback : DiffUtil.ItemCallback<FitnessEntity>() {
        override fun areItemsTheSame(oldItem: FitnessEntity, newItem: FitnessEntity): Boolean {
            return oldItem.exerciseName == newItem.exerciseName
        }

        override fun areContentsTheSame(oldItem: FitnessEntity, newItem: FitnessEntity): Boolean {
            return oldItem == newItem
        }
    }
}
