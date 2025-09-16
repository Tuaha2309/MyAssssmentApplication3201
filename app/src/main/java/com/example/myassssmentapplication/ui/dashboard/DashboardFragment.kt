package com.example.myassssmentapplication.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myassssmentapplication.R
import com.example.myassssmentapplication.databinding.FragmentDashboardBinding
import com.example.myassssmentapplication.ui.common.DashboardUiState
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var fitnessAdapter: FitnessAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupClickListeners()
        observeViewModel()

        val keypass = arguments?.getString("keypass") ?: "fitness"
        viewModel.loadFitnessEntities(keypass)
    }

    private fun setupRecyclerView() {
        fitnessAdapter = FitnessAdapter { entity ->
            val bundle = Bundle().apply {
                putParcelable("entity", entity)
            }
            findNavController().navigate(R.id.detailsFragment, bundle)
        }

        binding.fitnessRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = fitnessAdapter
        }
    }

    private fun setupClickListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            val keypass = arguments?.getString("keypass") ?: "fitness"
            viewModel.loadFitnessEntities(keypass)
        }

        binding.retryButton.setOnClickListener {
            val keypass = arguments?.getString("keypass") ?: "fitness"
            viewModel.retry(keypass)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is DashboardUiState.Idle -> {
                        showContent(false)
                        showError(false)
                        showLoading(false)
                    }
                    is DashboardUiState.Loading -> {
                        showContent(false)
                        showError(false)
                        showLoading(true)
                    }
                    is DashboardUiState.Success -> {
                        showContent(true)
                        showError(false)
                        showLoading(false)
                        fitnessAdapter.submitList(state.entities)
                    }
                    is DashboardUiState.Error -> {
                        showContent(false)
                        showError(true)
                        showLoading(false)
                        binding.errorText.text = state.message
                    }
                }
            }
        }
    }

    private fun showLoading(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun showContent(show: Boolean) {
        binding.fitnessRecyclerView.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun showError(show: Boolean) {
        binding.errorLayout.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
