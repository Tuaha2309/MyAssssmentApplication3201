package com.example.myassssmentapplication.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myassssmentapplication.R
import com.example.myassssmentapplication.databinding.FragmentLoginBinding
import com.example.myassssmentapplication.ui.common.LoginUiState
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
        observeViewModel()
    }

    private fun setupClickListeners() {
        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            viewModel.login(username, password)
        }

        binding.errorText.setOnClickListener {
            viewModel.clearError()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is LoginUiState.Idle -> {
                        showLoading(false)
                        hideError()
                    }
                    is LoginUiState.Loading -> {
                        showLoading(true)
                        hideError()
                    }
                    is LoginUiState.Success -> {
                        showLoading(false)
                        navigateToDashboard(state.keypass)
                    }
                    is LoginUiState.Error -> {
                        showLoading(false)
                        showError(state.message)
                    }
                }
            }
        }
    }

    private fun showLoading(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
        binding.loginButton.isEnabled = !show
        binding.usernameEditText.isEnabled = !show
        binding.passwordEditText.isEnabled = !show
    }

    private fun showError(message: String) {
        binding.errorText.text = message
        binding.errorText.visibility = View.VISIBLE
    }

    private fun hideError() {
        binding.errorText.visibility = View.GONE
    }

    private fun navigateToDashboard(keypass: String) {
        val bundle = Bundle().apply {
            putString("keypass", keypass)
        }
        findNavController().navigate(R.id.dashboardFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
