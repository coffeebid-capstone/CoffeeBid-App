package com.aplimelta.coffeebidapp.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.aplimelta.coffeebidapp.databinding.FragmentDetailBinding
import com.aplimelta.coffeebidapp.ui.main.activity.MainActivity
import com.aplimelta.coffeebidapp.utils.load

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        (activity as MainActivity).setupToolbar(binding?.toolbar)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val product = DetailFragmentArgs.fromBundle(arguments as Bundle).product

            binding?.apply {
                sivDetailPhoto.load(product.productPict.toString())
                auctionDetail.apply {
                    tvDetailPrice.text = product.openPrice.toString()
                    tvDetailAuctionStart.text = product.startDate
                    tvDetailAuctionEnd.text = product.endDate
                    tvDetailUsername.text = product.user?.username
                }

                productDetail.apply {
                    tvDetailDescription.text = product.description
                    tvDetailType.text = product.type
                }

                btnDetailAuction.setOnClickListener {
                    Toast.makeText(requireContext(), "Fitur belum jadi", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}