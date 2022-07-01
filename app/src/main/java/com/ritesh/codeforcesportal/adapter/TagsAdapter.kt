package com.ritesh.codeforcesportal.adapter

import android.content.Context
import com.ritesh.codeforcesportal.model.Tags.name
import com.ritesh.codeforcesportal.model.Tags.percent
import com.ritesh.codeforcesportal.model.Tags.triedCount
import com.ritesh.codeforcesportal.model.Tags.solvedCount
import androidx.recyclerview.widget.RecyclerView
import com.ritesh.codeforcesportal.model.Tags
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.ritesh.codeforcesportal.R
import android.widget.TextView
import android.widget.RelativeLayout
import java.util.*

class TagsAdapter(private val context: Context, private val PASTEL_COLORS: IntArray) :
    RecyclerView.Adapter<TagsAdapter.MyViewHolder>() {
    private var tagsList: List<Tags>? = null
    private val colorsLen: Int
    private val random: Random
    fun updateList(list: List<Tags>?) {
        tagsList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.tags_detail_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val tags = tagsList!![position]
        holder.tagName.text = tags.name
        holder.tagPercent.text = tags.percent
        holder.triedCount.text = tags.triedCount.toString()
        holder.solvedCount.text = tags.solvedCount.toString()
        holder.tag_bg.setBackgroundColor(PASTEL_COLORS[random.nextInt(colorsLen)])
    }

    override fun getItemCount(): Int {
        return if (tagsList == null) 0 else tagsList!!.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tagName: TextView
        val tagPercent: TextView
        val solvedCount: TextView
        val triedCount: TextView
        val tag_bg: RelativeLayout

        init {
            tagName = itemView.findViewById(R.id.tv_tag)
            tagPercent = itemView.findViewById(R.id.tv_percent)
            solvedCount = itemView.findViewById(R.id.tv_solvedCount)
            triedCount = itemView.findViewById(R.id.tv_triedCount)
            tag_bg = itemView.findViewById(R.id.tag_bg)
        }
    }

    init {
        colorsLen = PASTEL_COLORS.size
        random = Random()
    }
}