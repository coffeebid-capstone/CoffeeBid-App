package com.aplimelta.coffeebidapp.ui.main.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.aplimelta.coffeebidapp.data.source.Result
import com.aplimelta.coffeebidapp.databinding.FragmentProfileBinding
import com.aplimelta.coffeebidapp.ui.MainViewModel
import com.aplimelta.coffeebidapp.ui.ViewModelFactory
import com.aplimelta.coffeebidapp.ui.auth.activity.AuthActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding

    private val viewModel: MainViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            binding?.apply {
                viewModel.profile.observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Result.Error -> {
                            binding?.progressBar?.progressBar?.visibility = View.INVISIBLE
                            Toast.makeText(
                                requireActivity(),
                                result.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        Result.Loading -> binding?.progressBar?.progressBar?.visibility = View.VISIBLE


                        is Result.Success -> {
                            binding?.progressBar?.progressBar?.visibility = View.INVISIBLE
                            val data = result.data
                            if (data != null) {
                                tvProfileName.text = data.username
                                tvProfileEmail.text = data.email
                            }
                        }
                    }
                }

                actionLogout.setOnClickListener {
                    viewModel.logout.observe(viewLifecycleOwner) {
                        Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
                        startActivity(Intent(requireActivity(), AuthActivity::class.java))
                        requireActivity().finish()
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