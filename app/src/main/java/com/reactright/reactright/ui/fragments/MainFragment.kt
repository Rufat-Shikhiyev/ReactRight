package com.reactright.reactright.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.reactright.reactright.R
import com.reactright.reactright.databinding.FragmentMainBinding
import com.reactright.reactright.ui.adapter.MainAdapter
import com.reactright.reactright.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.rvMain.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        adapter = MainAdapter(requireContext(), mutableListOf())
        binding.rvMain.adapter = adapter

        // Set up the observer for itemList
        viewModel.getAll().observe(viewLifecycleOwner) { items ->
            adapter.itemList = items.toMutableList()
            adapter.notifyDataSetChanged()
        }


        return binding.root
    }
}
