package com.ritesh.codeforcesvisualizer.ui.contest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.ritesh.codeforcesvisualizer.databinding.FragmentContestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContestFragment : Fragment() {

    private val viewModel: ContestViewModel by viewModels()

    private var _binding: FragmentContestBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentContestBinding.inflate(inflater)

        val adapter = ContestAdapter()
        binding.contestsList.adapter = adapter


        viewModel.contestState.observe(viewLifecycleOwner) { state->
            adapter.submitList(state.data)
            binding.progressBar.visibility = state.isLoading
            if (state.showError) {
                Snackbar.make(binding.root,state.errorMessage!!,Snackbar.LENGTH_SHORT).apply {
                    animationMode = Snackbar.ANIMATION_MODE_SLIDE
                }.show()
            }
        }

        binding.contestRefreshLayout.setOnRefreshListener {
            viewModel.getContestList()
            binding.contestRefreshLayout.isRefreshing = false
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}