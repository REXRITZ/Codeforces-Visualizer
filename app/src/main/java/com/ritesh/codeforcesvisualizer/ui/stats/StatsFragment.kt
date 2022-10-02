package com.ritesh.codeforcesvisualizer.ui.stats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ritesh.codeforcesvisualizer.databinding.FragmentStatsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatsFragment : Fragment() {

    private lateinit var _binding: FragmentStatsBinding
    private val binding: FragmentStatsBinding
    get() = _binding

    private val viewModel: StatsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatsBinding.inflate(inflater)


        viewModel.verdictData.observe(viewLifecycleOwner) {
            binding.verdictPieChart.setTitle("Verdicts")
            binding.verdictPieChart.setData(it)
        }

        viewModel.tagsData.observe(viewLifecycleOwner) {
            binding.tagsPieChart.setTitle("Tags")
            binding.tagsPieChart.setData(it)
        }

        viewModel.progressIndicator.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = it
        }

        binding.statsRefreshLayout.setOnRefreshListener {
            viewModel.getUserSubmissions()
            binding.statsRefreshLayout.isRefreshing = false
        }

        return binding.root
    }



}