package com.ritesh.codeforcesportal.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.ritesh.codeforcesportal.R
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView

class UnsolvedAdapter(private val context: Context) :
    RecyclerView.Adapter<UnsolvedAdapter.MyViewHolder>() {
    private var problemNames: List<String>? = null
    fun updateList(list: List<String>?) {
        problemNames = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.unsolved_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val problemName = problemNames!![position]
        holder.problemName.text = problemName
        holder.link.setOnClickListener(View.OnClickListener {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse(
                        "https://codeforces.com/problemset/problem/$problemName"
                    )
                )
            )
        })
    }

    override fun getItemCount(): Int {
        return if (problemNames == null) 0 else problemNames!!.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var problemName: TextView
        var link: CircleImageView

        init {
            problemName = itemView.findViewById(R.id.problem_name)
            link = itemView.findViewById(R.id.goto_problem)
        }
    }
}