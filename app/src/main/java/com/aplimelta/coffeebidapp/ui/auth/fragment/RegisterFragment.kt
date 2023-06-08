package com.aplimelta.coffeebidapp.ui.auth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aplimelta.coffeebidapp.R
import com.aplimelta.coffeebidapp.data.source.remote.request.SignUpRequest
import com.aplimelta.coffeebidapp.databinding.FragmentRegisterBinding
import com.aplimelta.coffeebidapp.ui.MainViewModel
import com.aplimelta.coffeebidapp.ui.ViewModelFactory

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding

    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance()
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

            val password = binding?.cetRegisterPassword?.text?.trim().toString()
            val address = binding?.cetRegisterAddress?.text?.trim().toString()
            val role = "admin"
            val contact = binding?.cetRegisterPhone?.text?.trim().toString()
            val confirmPassword = binding?.cetRegisterPasswordConfirm?.text?.trim().toString()
            val email = binding?.cetRegisterEmail?.text?.trim().toString()
            val username = binding?.cetRegisterName?.text?.trim().toString()

            binding?.btnRegister?.setOnClickListener {
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
                ).observe(viewLifecycleOwner) {
                    Toast.makeText(requireContext(), "Success:${it}", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.login_navigation)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}