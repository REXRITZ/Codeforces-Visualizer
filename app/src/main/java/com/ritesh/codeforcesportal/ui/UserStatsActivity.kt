package com.ritesh.codeforcesportal.ui

import android.graphics.Color
import android.util.Pair
import android.view.View
import androidx.lifecycle.Observer
import com.github.mikephil.charting.data.Entry
import com.ritesh.codeforcesportal.model.Tags
import com.ritesh.codeforcesportal.model.User
import com.ritesh.codeforcesportal.utils.Utils
import java.text.DecimalFormat
import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet

class UserStatsActivity : AppCompatActivity() {
    private var verdictChart: PieChart? = null
    private var ratingChart: BarChart? = null
    var PASTEL_COLORS: IntArray
    private var tagsRecyclerView: RecyclerView? = null
    private var userRecyclerView: RecyclerView? = null
    private var unsolvedRecyclerView: RecyclerView? = null
    private var tagsAdapter: TagsAdapter? = null
    private var userAdapter: UserAdapter? = null
    private var unsolvedAdapter: UnsolvedAdapter? = null
    private var userName: TextView? = null
    private var userRank: TextView? = null
    private var imageView: CircleImageView? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_stats)
        PASTEL_COLORS = getResources().getIntArray(R.array.pastel_colors)
        initViews()
        val user: User = getIntent().getParcelableExtra<User>("userProfile")
        displayUserData(user)
        setPieChart()
        val viewModel: UserStatsViewModel =
            ViewModelProvider(this).get(UserStatsViewModel::class.java)
        viewModel.init(if (user != null) user.handle else "")
        viewModel.getUserSubmissions()
            .observe(this, Observer<List<Any>?> { submissions -> createChartData(submissions) })
    }

    private fun initViews() {
        userName = findViewById<TextView>(R.id.userName)
        userRank = findViewById<TextView>(R.id.userRank)
        imageView = findViewById<CircleImageView>(R.id.profile_photo)
        verdictChart = findViewById<PieChart>(R.id.piechart)
        ratingChart = findViewById<BarChart>(R.id.barChart)
        unsolvedRecyclerView = findViewById<RecyclerView>(R.id.unsolved_prob_recyclerview)
        unsolvedRecyclerView.setNestedScrollingEnabled(false)
        unsolvedRecyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        unsolvedRecyclerView.setLayoutManager(layoutManager)
        unsolvedRecyclerView.addItemDecoration(
            DividerItemDecoration(
                unsolvedRecyclerView.getContext(),
                layoutManager.getOrientation()
            )
        )
        unsolvedAdapter = UnsolvedAdapter(this)
        unsolvedRecyclerView.setAdapter(unsolvedAdapter)
        tagsRecyclerView = findViewById<RecyclerView>(R.id.tags_recyclerview)
        tagsRecyclerView.setNestedScrollingEnabled(false)
        tagsRecyclerView.setHasFixedSize(true)
        tagsRecyclerView.setLayoutManager(LinearLayoutManager(this))
        tagsAdapter = TagsAdapter(this, PASTEL_COLORS)
        tagsRecyclerView.setAdapter(tagsAdapter)
        userRecyclerView = findViewById<RecyclerView>(R.id.user_info_recyclerview)
        userRecyclerView.setHasFixedSize(true)
        userRecyclerView.setNestedScrollingEnabled(false)
        val layoutManager1 = LinearLayoutManager(this)
        userRecyclerView.setLayoutManager(layoutManager1)
        userRecyclerView.addItemDecoration(
            DividerItemDecoration(
                userRecyclerView.getContext(),
                layoutManager1.getOrientation()
            )
        )
        userAdapter = UserAdapter(this)
        userRecyclerView.setAdapter(userAdapter)
        val root: ViewGroup = findViewById<ViewGroup>(R.id.root)
        root.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING)
        val clicked = BooleanArray(5)
        for (i in 0..4) clicked[i] = false
        val userExpand: MaterialButton
        val pieChartExpand: MaterialButton
        val barChartExpand: MaterialButton
        val tagsExpand: MaterialButton
        val unsolvedExpand: MaterialButton
        userExpand = findViewById<MaterialButton>(R.id.user_expand)
        pieChartExpand = findViewById<MaterialButton>(R.id.pie_expand)
        barChartExpand = findViewById<MaterialButton>(R.id.bar_expand)
        tagsExpand = findViewById<MaterialButton>(R.id.tags_expand)
        unsolvedExpand = findViewById<MaterialButton>(R.id.problems_expand)
        userExpand.setOnClickListener(View.OnClickListener {
            if (!clicked[0]) {
                userRecyclerView.setVisibility(View.VISIBLE)
                userExpand.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24),
                    null
                )
            } else {
                userRecyclerView.setVisibility(View.GONE)
                userExpand.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_right_24),
                    null
                )
            }
            clicked[0] = !clicked[0]
        })
        pieChartExpand.setOnClickListener(View.OnClickListener {
            if (!clicked[1]) {
                verdictChart.setVisibility(View.VISIBLE)
                pieChartExpand.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24),
                    null
                )
            } else {
                verdictChart.setVisibility(View.GONE)
                pieChartExpand.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_right_24),
                    null
                )
            }
            clicked[1] = !clicked[1]
        })
        barChartExpand.setOnClickListener(View.OnClickListener {
            if (!clicked[2]) {
                ratingChart.setVisibility(View.VISIBLE)
                barChartExpand.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24),
                    null
                )
            } else {
                ratingChart.setVisibility(View.GONE)
                barChartExpand.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_right_24),
                    null
                )
            }
            clicked[2] = !clicked[2]
        })
        tagsExpand.setOnClickListener(View.OnClickListener {
            if (!clicked[3]) {
                tagsRecyclerView.setVisibility(View.VISIBLE)
                tagsExpand.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24),
                    null
                )
            } else {
                tagsRecyclerView.setVisibility(View.GONE)
                tagsExpand.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_right_24),
                    null
                )
            }
            clicked[3] = !clicked[3]
        })
        unsolvedExpand.setOnClickListener(View.OnClickListener {
            if (!clicked[4]) {
                unsolvedRecyclerView.setVisibility(View.VISIBLE)
                unsolvedExpand.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24),
                    null
                )
            } else {
                unsolvedRecyclerView.setVisibility(View.GONE)
                unsolvedExpand.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_right_24),
                    null
                )
            }
            clicked[4] = !clicked[4]
        })
    }

    fun displayUserData(user: User?) {
        if (user == null) return
        Glide.with(this)
            .load(user.avatar)
            .placeholder(R.drawable.ic_placeholder_24)
            .into(imageView)
        imageView.setBorderColor(getResources().getColor(Utils.getRankColor(user.rating)))
        userName.setText(user.handle)
        userRank.setTextColor(getResources().getColor(Utils.getRankColor(user.rating)))
        userRank.setText(user.rank)

        //user data
        val userList = ArrayList<Pair<String, String?>>()
        userList.add(Pair("Handle", user.handle))
        userList.add(Pair("Rank", user.rank))
        userList.add(Pair("Rating", user.rating.toString()))
        if (user.firstName != null || user.lastName != null) userList.add(
            Pair(
                "Name",
                user.firstName + " " + user.lastName
            )
        )
        userList.add(Pair("Max rank", user.maxRank))
        userList.add(Pair("Max rating", user.maxRating.toString()))
        userAdapter.updateList(userList)
    }

    private fun createChartData(submissions: List<Submission>?) {
        Toast.makeText(this, "FFF", Toast.LENGTH_SHORT).show()
        if (submissions == null) return
        Toast.makeText(this, "UUU", Toast.LENGTH_SHORT).show()
        val verdictMap = HashMap<String, Float>()
        val ratingsMap = HashMap<Int, Float>()
        //tag -> {correct,total}
        val tagsSet: MutableMap<String, DoubleArray?> = HashMap()
        val unsolvedSet = HashSet<String>()
        for (submission in submissions) {
            val verdict: String = submission.verdict
            if (verdictMap.containsKey(verdict)) {
                verdictMap[verdict] = verdictMap[verdict]!! + 1f
            } else {
                verdictMap[verdict] = 1f
            }
            val problem: Problem = submission.problem
            //WARNING: assume for now that rating is valid
            if (verdict == "OK") {
                if (ratingsMap.containsKey(problem.rating)) {
                    ratingsMap[problem.rating] = ratingsMap[problem.rating]!! + 1f
                } else {
                    ratingsMap[problem.rating] = 1f
                }
            } else {
                val problemName: String = problem.contestId.toString() + "/" + problem.index
                unsolvedSet.add(problemName)
            }
            for (tag in problem.tags) {
                var `val`: DoubleArray?
                if (tagsSet.containsKey(tag)) {
                    `val` = tagsSet[tag]
                    if (verdict == "OK") `val`!![0] += 1
                    `val`!![1] += 1
                } else {
                    `val` = DoubleArray(2)
                    `val`[0] = 1.0
                    `val`[1] = 1.0
                }
                tagsSet[tag] = `val`
            }
        }
        val verdictsList: ArrayList<PieEntry> = ArrayList<PieEntry>()
        val ratingsList: ArrayList<BarEntry> = ArrayList<BarEntry>()
        for ((key, value) in verdictMap) {
            verdictsList.add(PieEntry(value, key))
        }
        for ((key, value) in ratingsMap) {
            ratingsList.add(BarEntry(key, value))
        }
        loadPieChartData(verdictsList, ratingsList)
        val decimalFormat = DecimalFormat()
        decimalFormat.applyPattern(".00")
        val tagsList: ArrayList<Tags> = ArrayList<Tags>()
        for (entry in tagsSet.entries) {
            addTagToList(entry, tagsList, decimalFormat)
        }
        tagsAdapter.updateList(tagsList)
        val unsolvedProblems: MutableList<String> = ArrayList()
        unsolvedProblems.addAll(unsolvedSet)
        unsolvedAdapter.updateList(unsolvedProblems)
    }

    private fun addTagToList(
        entry: Map.Entry<String, DoubleArray?>,
        tagsList: ArrayList<Tags>,
        decimalFormat: DecimalFormat
    ) {
        val tag = entry.key
        val solved = entry.value!![0]
        val total = entry.value!![1]
        val percent = solved / total * 100.0
        tagsList.add(Tags(tag, decimalFormat.format(percent), solved.toInt(), total.toInt()))
    }

    private fun loadPieChartData(
        verdictsList: ArrayList<PieEntry>?,
        ratingsList: ArrayList<BarEntry>
    ) {
        val colors = ArrayList<Int>()
        for (color in ColorTemplate.MATERIAL_COLORS) {
            colors.add(color)
        }
        for (color in ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color)
        }

        //Pie chart load
        val verdictDataSet = PieDataSet(verdictsList, "")
        verdictDataSet.setColors(colors)
        val verdictData = PieData(verdictDataSet)
        verdictData.setDrawValues(true)
        verdictData.setValueTextSize(12f)
        verdictData.setValueTextColor(Color.BLACK)
        verdictData.setValueFormatter(object : IValueFormatter {
            override fun getFormattedValue(
                value: Float,
                entry: Entry,
                ind: Int,
                viewPortHandler: ViewPortHandler
            ): String {
                if (verdictsList != null && verdictsList.size > 0 && value / verdictsList.size < 0.15) {
                    val entry1: PieEntry = entry as PieEntry
                    entry1.setLabel("")
                    return ""
                }
                return String.format("%.2f", value)
            }
        })
        //pie chart update
        verdictChart.setData(verdictData)
        verdictChart.invalidate()
        verdictChart.animateY(1400, Easing.EasingOption.EaseInOutQuad)

        //Bar chart load
        val set = BarDataSet(ratingsList, "Ratings")
        set.setColors(colors)
        val dataSets: ArrayList<IBarDataSet> = ArrayList<IBarDataSet>()
        dataSets.add(set)
        val data = BarData(set)
        data.setBarWidth(50f)
        //Bar chart update
        ratingChart.setFitBars(true)
        ratingChart.setData(data)
        ratingChart.setPinchZoom(false)
        ratingChart.setDragEnabled(true)
        ratingChart.getAxisRight().setEnabled(false)
        ratingChart.getXAxis().setDrawGridLines(false)
        ratingChart.getXAxis().setAxisMinimum(100f)
        ratingChart.getXAxis().setGranularity(400f)
        ratingChart.animateY(1400)
        ratingChart.setVisibleXRangeMaximum(1500f)
        ratingChart.invalidate()
    }

    private fun setPieChart() {
        verdictChart.setDrawHoleEnabled(false)
        verdictChart.setUsePercentValues(true)
        verdictChart.setEntryLabelTextSize(12f)
        verdictChart.setEntryLabelColor(Color.BLACK)
        verdictChart.getDescription().setEnabled(false)
        val l: Legend = verdictChart.getLegend()
        //        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP)
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT)
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL)
        ratingChart.setDrawBarShadow(false)
        //        ratingChart.setDrawValueAboveBar(false);
        ratingChart.getDescription().setEnabled(false)
        //        ratingChart.setPinchZoom(false);
        ratingChart.setDrawGridBackground(false)
        ratingChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM)
    }
}