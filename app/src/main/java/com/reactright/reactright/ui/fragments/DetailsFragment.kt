package com.reactright.reactright.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.reactright.reactright.databinding.FragmentDetailsBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDetailsBinding.inflate(inflater, container, false)

        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        val bundle: DetailsFragmentArgs by navArgs()
        val item = bundle.item

        binding.titleDetail.text = item.title
        binding.titleToolbar.text = item.title
        binding.describeDetail.text = item.describe

        Picasso.get().load(item.mainImage).into(binding.imageDetail)


        return binding.root
    }
}