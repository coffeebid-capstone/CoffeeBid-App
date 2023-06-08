package com.aplimelta.coffeebidapp.ui.auth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.aplimelta.coffeebidapp.data.source.remote.request.SignInRequest
import com.aplimelta.coffeebidapp.databinding.FragmentLoginBinding
import com.aplimelta.coffeebidapp.ui.MainViewModel
import com.aplimelta.coffeebidapp.ui.ViewModelFactory
import com.aplimelta.coffeebidapp.ui.auth.activity.AuthActivity

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding

    private val viewModel: MainViewModel by activityViewModels {
        ViewModelFactory.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val isPassword = binding?.cetLoginPassword
            val isEmail = binding?.cetLoginEmail

            binding?.btnLogin?.setOnClickListener {
                if (isPassword != null && isEmail != null) {
                    val password = isPassword.text?.trim().toString()
                    val email = isEmail.text?.trim().toString()

                    viewModel.signIn(
                        SignInRequest(
                            password = password,
                            email = email,
                        )
                    ).observe(viewLifecycleOwner) {
                        Toast.makeText(requireContext(), "Success:${it}", Toast.LENGTH_LONG).show()
                        (activity as AuthActivity).directToMainActivity()
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