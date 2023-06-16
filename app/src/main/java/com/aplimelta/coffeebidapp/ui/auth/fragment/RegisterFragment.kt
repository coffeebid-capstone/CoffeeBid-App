package com.aplimelta.coffeebidapp.ui.auth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.aplimelta.coffeebidapp.R
import com.aplimelta.coffeebidapp.data.source.Result
import com.aplimelta.coffeebidapp.data.source.remote.request.SignUpRequest
import com.aplimelta.coffeebidapp.databinding.FragmentRegisterBinding
import com.aplimelta.coffeebidapp.ui.MainViewModel
import com.aplimelta.coffeebidapp.ui.ViewModelFactory

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding

    private val viewModel: MainViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val etUsername = binding?.cetRegisterName
            val etEmail = binding?.cetRegisterEmail
            val etPassword = binding?.cetRegisterPassword
            val etPasswordConfirm = binding?.cetRegisterPasswordConfirm
            val etAddress = binding?.cetRegisterAddress
            val etPhone = binding?.cetRegisterPhone

            binding?.btnRegister?.setOnClickListener {
                if (etUsername != null && etEmail != null && etPassword != null && etPasswordConfirm != null && etAddress != null && etPhone != null) {
                    val password = etPassword.text?.trim().toString()
                    val address = etAddress.text?.trim().toString()
                    val role = "admin"
                    val contact = etPhone.text?.trim().toString()
                    val confirmPassword = etPasswordConfirm.text?.trim().toString()
                    val email = etEmail.text?.trim().toString()
                    val username = etUsername.text?.trim().toString()

                    viewModel.signUp(
                        SignUpRequest(
                            password = password,
                            address = address,
                            role = role,
                            contact = contact,
                            confirmPassword = confirmPassword,
                            email = email,
                            username = username,
                        )
                    ).observe(viewLifecycleOwner) { result ->
                        when (result) {
                            is Result.Error -> {
                                binding?.progressBar?.progressBar?.visibility = View.INVISIBLE
                                Toast.makeText(
                                    requireContext(),
                                    result.message,
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                            Result.Loading -> binding?.progressBar?.progressBar?.visibility =
                                View.VISIBLE

                            is Result.Success -> {
                                binding?.progressBar?.progressBar?.visibility = View.INVISIBLE
                                Toast.makeText(
                                    requireContext(),
                                    result.data,
                                    Toast.LENGTH_LONG
                                ).show()
                                findNavController().navigate(R.id.login_navigation)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}