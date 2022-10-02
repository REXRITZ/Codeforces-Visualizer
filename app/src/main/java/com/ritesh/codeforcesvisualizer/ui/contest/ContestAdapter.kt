package com.ritesh.codeforcesvisualizer.ui.contest

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
import com.ritesh.codeforcesvisualizer.databinding.ContestItemBinding
import com.ritesh.codeforcesvisualizer.model.Contest
import com.ritesh.codeforcesvisualizer.util.Utils.toFormattedTime
import java.text.SimpleDateFormat
import java.util.*

class ContestAdapter: ListAdapter<Contest,ContestAdapter.ContestViewHolder>(ContestItemDiffCallback()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContestViewHolder {
        val binding: ContestItemBinding = ContestItemBinding.inflate(LayoutInflater.from(parent.context))
        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        binding.root.layoutParams = params
        return ContestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContestViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class ContestViewHolder(val binding: ContestItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(contest: Contest) {
            binding.contestName.text = contest.name
            binding.contestStatus.text = contest.phase
            binding.contestStartTime.text = toFormattedTime(contest.startTime)

            binding.root.setOnClickListener {
                if (binding.moreOptions.isVisible)
                    binding.moreOptions.visibility = View.GONE
                else
                    binding.moreOptions.visibility = View.VISIBLE
            }

            binding.register.setOnClickListener {
                val url = "${CodeforcesApi.BASE_URL}/contestRegistration/${contest.id}"
                val intent = Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse(url)
                }
                binding.root.context.startActivity(intent)
            }

            binding.reminder.setOnClickListener {

                val intent = Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,contest.startTime)
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, contest.startTime + contest.duration)
                    .putExtra(CalendarContract.Events.TITLE, contest.name)
                binding.root.context.startActivity(intent)
            }
        }

    }

    class ContestItemDiffCallback: DiffUtil.ItemCallback<Contest>() {
        override fun areItemsTheSame(oldItem: Contest, newItem: Contest): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Contest, newItem: Contest): Boolean {
            return oldItem == newItem
        }

    }
}