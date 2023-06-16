package com.aplimelta.coffeebidapp.ui.auth.fragment

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.aplimelta.coffeebidapp.data.source.Result
import com.aplimelta.coffeebidapp.data.source.remote.request.SignInRequest
import com.aplimelta.coffeebidapp.databinding.FragmentLoginBinding
import com.aplimelta.coffeebidapp.ui.MainViewModel
import com.aplimelta.coffeebidapp.ui.ViewModelFactory
import com.aplimelta.coffeebidapp.ui.auth.activity.AuthActivity

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding

    private val viewModel: MainViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext())
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
            val isEmail = binding?.cetLoginEmail
            val isPassword = binding?.cetLoginPassword

            binding?.btnLogin?.setOnClickListener {
                if (isEmail != null && isPassword != null) {
                    val password = isPassword.text?.trim().toString()
                    val email = isEmail.text?.trim().toString()

                    viewModel.signIn(
                        SignInRequest(
                            password = password,
                            email = email,
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

                            is Result.Success -> {
                                binding?.progressBar?.progressBar?.visibility = View.INVISIBLE
                                Toast.makeText(
                                    requireContext(),
                                    "${result.data}",
                                    Toast.LENGTH_LONG
                                ).show()
                                (activity as AuthActivity).directToMainActivity()
                            }

                            else -> {
                                binding?.progressBar?.progressBar?.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }

            playAnimation()
        }
    }

    private fun playAnimation() {
        val titleHero =
            ObjectAnimator.ofFloat(binding?.tvTitleLogin, View.ALPHA, 1F).setDuration(500)
        val subTitleHero =
            ObjectAnimator.ofFloat(binding?.tvSubtitleLogin, View.ALPHA, 1F).setDuration(500)
        val etEmail =
            ObjectAnimator.ofFloat(binding?.cetLoginEmail, View.ALPHA, 1F).setDuration(500)
        val etPassword =
            ObjectAnimator.ofFloat(binding?.cetLoginPassword, View.ALPHA, 1F).setDuration(500)
        val btnLogin =
            ObjectAnimator.ofFloat(binding?.btnLogin, View.ALPHA, 1F).setDuration(500)

        AnimatorSet().apply {
            playSequentially(
                titleHero,
                subTitleHero,
                etEmail,
                etPassword,
                btnLogin
            )
            startDelay = 500
            start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}