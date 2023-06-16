package com.aplimelta.coffeebidapp.ui.main.fragment

import android.app.SearchManager
import android.content.Intent
import android.content.SearchRecentSuggestionsProvider
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aplimelta.coffeebidapp.R
import com.aplimelta.coffeebidapp.data.source.Result
import com.aplimelta.coffeebidapp.data.source.model.ProductModel
import com.aplimelta.coffeebidapp.data.source.remote.response.ProductResponseItem
import com.aplimelta.coffeebidapp.data.source.remote.response.SearchProductResponseItem
import com.aplimelta.coffeebidapp.databinding.FragmentHomeBinding
import com.aplimelta.coffeebidapp.ui.CoffeeAdapter
import com.aplimelta.coffeebidapp.ui.MainViewModel
import com.aplimelta.coffeebidapp.ui.ViewModelFactory
import com.aplimelta.coffeebidapp.utils.MappingHelper
import com.aplimelta.coffeebidapp.utils.toast

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private val viewModel: MainViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private lateinit var adapter: CoffeeAdapter
    private lateinit var scheduleAdapter: CoffeeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            adapter = CoffeeAdapter()
            scheduleAdapter = CoffeeAdapter()

            if (Intent.ACTION_SEARCH == requireActivity().intent.action) {
                arguments?.getString(SearchManager.QUERY).also { query ->
                    SearchRecentSuggestions(
                        requireActivity(),
                        SuggestionProvider.AUTHORITY,
                        SuggestionProvider.MODE
                    ).saveRecentQuery(query, null)
                }
            }

            binding?.svSearchView?.editText?.setOnEditorActionListener { v, actionId, event ->
                val text = v.text
                if (text.toString() != "") {
                    viewModel.searchProductByName(text.toString())
                        .observe(viewLifecycleOwner) { result: Result<List<SearchProductResponseItem>?> ->
                            when (result) {
                                is Result.Error -> {
                                    binding?.progressBar?.progressBar?.visibility = View.INVISIBLE
                                    showMessageEmpty(false)
                                    showMessageError(true)
                                }

                                Result.Loading -> {
                                    binding?.progressBar?.progressBar?.visibility = View.VISIBLE
                                    showMessageEmpty(false)
                                    showMessageError(false)
                                }

                                is Result.Success -> {
                                    val searchCoffees = result.data
                                    if (searchCoffees != null) {
                                        binding?.progressBar?.progressBar?.visibility = View.INVISIBLE
                                        showMessageEmpty(false)
                                        showMessageError(false)
                                        val data =
                                            MappingHelper.searchProductResponseToModel(searchCoffees)
                                        val product = MappingHelper.searchToProductModel(data)
                                        adapter.submitList(product)
                                    } else {
                                        binding?.progressBar?.progressBar?.visibility = View.INVISIBLE
                                        showMessageError(false)
                                        showMessageEmpty(true)
                                    }
                                }
                            }
                        }
                }

                binding?.sbSearchBar?.text = text ?: null
                binding?.svSearchView?.hide()
                false
            }

            viewModel.coffees.observe(viewLifecycleOwner) { result: Result<List<ProductResponseItem>?> ->
                when (result) {
                    is Result.Error -> {
                        binding?.progressBar?.progressBar?.visibility = View.INVISIBLE
                        showMessageEmpty(false)
                        showMessageError(true)
                    }

                    Result.Loading -> {
                        binding?.progressBar?.progressBar?.visibility = View.VISIBLE
                        showMessageEmpty(false)
                        showMessageError(false)
                    }

                    is Result.Success -> {
                        val coffees = result.data
                        if (coffees != null) {
                            binding?.progressBar?.progressBar?.visibility = View.INVISIBLE
                            showMessageEmpty(false)
                            showMessageError(false)
                            val data = MappingHelper.productResponseToModel(coffees)
                            adapter.submitList(data)
                        } else {
                            binding?.progressBar?.progressBar?.visibility = View.INVISIBLE
                            showMessageError(false)
                            showMessageEmpty(true)
                        }
                    }
                }
            }

            showRecyclerView()
        }
    }


    private fun showRecyclerView() {
        val layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding?.apply {
            rvRunning.setHasFixedSize(true)
            rvRunning.layoutManager = layoutManager
            rvRunning.adapter = adapter
        }

        adapter.setItemClickCallback(object : CoffeeAdapter.OnItemClickCallback {
            override fun onItemClicked(product: ProductModel) {
                val detailDestination =
                    HomeFragmentDirections.actionHomeNavigationToDetailNavigation(product)
                findNavController().navigate(detailDestination)
            }
        })
    }

    private fun showMessageEmpty(state: Boolean) {
        binding?.apply {
            layoutMessage.root.visibility = if (state) {
                layoutMessage.sivIllustrationImage.setImageResource(R.drawable.ic_undraw_empty)
                layoutMessage.tvTitleMessage.text = resources.getString(R.string.empty_message)
                layoutMessage.tvSubtitleMessage.text =
                    resources.getString(R.string.empty_message_subtitle)
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
        }
    }

    private fun showMessageError(state: Boolean) {
        binding?.apply {
            layoutMessage.root.visibility = if (state) {
                layoutMessage.sivIllustrationImage.setImageResource(R.drawable.ic_undraw_server_cluster)
                layoutMessage.tvTitleMessage.text = resources.getString(R.string.error_message)
                layoutMessage.tvTitleMessage.text =
                    resources.getString(R.string.error_message_subtitle)
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        binding?.rvRunning?.adapter = null
    }

    internal class SuggestionProvider : SearchRecentSuggestionsProvider() {
        init {
            setupSuggestions(AUTHORITY, MODE)
        }

        companion object {
            const val AUTHORITY = "com.aplimelta.coffeebidapp.SuggestionProvider"
            const val MODE: Int = DATABASE_MODE_QUERIES
        }
    }
}