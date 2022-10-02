package com.ritesh.codeforcesvisualizer.ui.user

import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ritesh.codeforcesvisualizer.data.remote.CodeforcesApi
import com.ritesh.codeforcesvisualizer.databinding.BlogItemBinding
import com.ritesh.codeforcesvisualizer.model.Blog
import com.ritesh.codeforcesvisualizer.util.Utils
import com.ritesh.codeforcesvisualizer.util.Utils.toFormattedTime

class BlogAdapter: ListAdapter<Blog, BlogAdapter.BlogViewHolder>(BlogItemDiffCallback()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val binding: BlogItemBinding = BlogItemBinding.inflate(LayoutInflater.from(parent.context))
        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        binding.root.layoutParams = params
        return BlogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class BlogViewHolder(val binding: BlogItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(blog: Blog) {

            binding.blogTitle.text = blog.title
            binding.date.text = toFormattedTime(blog.lastModified)

            binding.root.setOnClickListener {
                val url = "${CodeforcesApi.BASE_URL}/blog/entry/${blog.id}"
                val intent = Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse(url)
                }
                binding.root.context.startActivity(intent)
            }

        }

    }

    class BlogItemDiffCallback: DiffUtil.ItemCallback<Blog>() {
        override fun areItemsTheSame(oldItem: Blog, newItem: Blog): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Blog, newItem: Blog): Boolean {
            return oldItem == newItem
        }

    }
}