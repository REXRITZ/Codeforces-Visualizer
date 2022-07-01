package com.ritesh.codeforcesportal.ui

import com.ritesh.codeforcesportal.adapter.GenericAdapter
import java.util.*

class ContestActivity : AppCompatActivity() {
    private var upComingContest: MutableList<Contest?>? = null
    private var onGoingContest: MutableList<Contest>? = null
    private var finishedContest: MutableList<Contest>? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contest)
        upComingContest = ArrayList<Contest?>()
        onGoingContest = ArrayList<Contest>()
        finishedContest = ArrayList<Contest>()
        loadData(getIntent().getParcelableArrayListExtra<Contest>("contests"))
    }

    private fun loadData(contests: ArrayList<Contest>?) {
        if (contests == null) return
        for (contest in contests) {
            val phase: String = contest.phase
            when (phase) {
                "BEFORE" -> upComingContest!!.add(contest)
                "CODING" -> onGoingContest!!.add(contest)
                "FINISHED" -> finishedContest!!.add(contest)
            }
        }
        Collections.reverse(upComingContest)
    }
}