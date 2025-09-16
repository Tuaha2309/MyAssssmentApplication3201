package com.example.myassssmentapplication.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myassssmentapplication.databinding.FragmentDetailsBinding
import com.example.myassssmentapplication.domain.model.FitnessEntity
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val entity = arguments?.getParcelable("entity", FitnessEntity::class.java)
        entity?.let { displayEntity(it) }
    }

    private fun displayEntity(entity: FitnessEntity) {
        binding.apply {
            exerciseName.text = entity.exerciseName
            muscleGroup.text = entity.muscleGroup
            difficulty.text = entity.difficulty
            equipment.text = entity.equipment
            caloriesChip.text = "${entity.caloriesBurnedPerHour} kcal/hr"
            description.text = entity.description
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
