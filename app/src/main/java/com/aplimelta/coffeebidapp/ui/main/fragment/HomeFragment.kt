package com.aplimelta.coffeebidapp.ui.main.fragment

import android.app.SearchManager
import android.content.Intent
import android.content.SearchRecentSuggestionsProvider
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aplimelta.coffeebidapp.data.source.Result
import com.aplimelta.coffeebidapp.data.source.model.ProductModel
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
                        .observe(viewLifecycleOwner) { result ->
                            when (result) {
                                is Result.Error -> requireActivity().toast(result.message)

                                Result.Loading -> requireActivity().toast("Loading...")

                                is Result.Success -> {
                                    val searchCoffees = result.data
                                    val data =
                                        MappingHelper.searchProductResponseToModel(searchCoffees)
                                    val product = MappingHelper.searchToProductModel(data)
                                    adapter.submitList(product)
                                }
                            }
                        }
                }

                Log.d("Home", "onViewCreated: $text")
                binding?.sbSearchBar?.text = text ?: null
                binding?.svSearchView?.hide()
                false
            }

            viewModel.coffees.observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Error -> {
                        binding?.progressBar?.progressBar?.visibility = View.INVISIBLE
                        Toast.makeText(
                            requireActivity(), result.message, Toast.LENGTH_LONG
                        ).show()
                    }

                    Result.Loading -> binding?.progressBar?.progressBar?.visibility = View.VISIBLE

                    is Result.Success -> {
                        binding?.progressBar?.progressBar?.visibility = View.INVISIBLE
                        val coffees = result.data
                        val data = MappingHelper.productResponseToModel(coffees)
                        Log.d("Home", "onViewCreated: $data")
                        adapter.submitList(data)
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