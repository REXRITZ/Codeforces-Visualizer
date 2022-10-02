package com.ritesh.codeforcesvisualizer.ui.user

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.ritesh.codeforcesvisualizer.R
import com.ritesh.codeforcesvisualizer.databinding.FragmentContestBinding
import com.ritesh.codeforcesvisualizer.databinding.FragmentUserBinding
import com.ritesh.codeforcesvisualizer.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment() {

    private val viewModel: UserViewModel by viewModels()
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater)

        val adapter = BlogAdapter()
        binding.blogsList.adapter = adapter

        viewModel.blogs.observe(viewLifecycleOwner) {
            adapter.submitList(it.data)
            binding.progressBar.visibility = it.isLoading
            if (it.showError) {
                Snackbar.make(binding.root,it.errorMessage!!,Snackbar.LENGTH_SHORT).apply {
                    animationMode = Snackbar.ANIMATION_MODE_SLIDE
                }.show()
            }
        }

        viewModel.user.observe(viewLifecycleOwner) {
            if (it != null) {
                fillUserData(it)
            }
        }

        binding.userSearch.editText!!.setOnEditorActionListener { textView, id, keyEvent ->
            if (id == EditorInfo.IME_ACTION_DONE) {
                val userName = textView.text.toString()
                if(userName.isEmpty()) {
                    binding.userSearch.error = "username cannot be empty"
                    return@setOnEditorActionListener false
                }
                viewModel.getData(userName)
                return@setOnEditorActionListener true
            }
            false
        }

        return binding.root
    }

    private fun fillUserData(user: User) {
        Glide.with(requireContext()).load(user.photoUrl).into(binding.titlePhoto)
        binding.name.text = user.name
        binding.handle.text = user.handle
        binding.rank.text = user.rank
        binding.rating.text = user.rating.toString()
        binding.maxRank.text = user.maxRank
        binding.maxRating.text = user.maxRating.toString()
        binding.userToolbar.title = "Hello, ${user.handle}"
    }


}