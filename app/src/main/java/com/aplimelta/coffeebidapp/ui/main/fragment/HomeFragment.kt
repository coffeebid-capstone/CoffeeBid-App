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
import com.aplimelta.coffeebidapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

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
                false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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