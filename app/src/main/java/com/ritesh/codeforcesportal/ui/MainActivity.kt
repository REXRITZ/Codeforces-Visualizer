package com.ritesh.codeforcesportal.ui

import android.app.AlertDialog
import android.view.View
import androidx.lifecycle.Observer
import com.ritesh.codeforcesportal.model.Status
import com.ritesh.codeforcesportal.model.User
import com.ritesh.codeforcesportal.utils.Utils
import java.lang.StringBuilder
import java.util.*

class MainActivity : AppCompatActivity() {
    private var adapter: ContestAdapter? = null
    private var ratingProgress: LinearProgressIndicator? = null
    private var userName: TextView? = null
    private var fullName: TextView? = null
    private var userRank: TextView? = null
    private var userRating: TextView? = null
    private var profilePic: CircleImageView? = null
    private var user: User? = null
    private var contests: ArrayList<Contest>? = null
    private var loadingScreen: AlertDialog? = null
    private var status: Status? = null
    private var comment: String? = null
    private var handle: String? = null
    private var preferences: SharedPreferences? = null
    private var viewModel: MainViewModel? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        handle = preferences.getString("handle", "tourist")
        initViews()
        loadLoadingDialog()
        contests = ArrayList<Contest>()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.init()
        viewModel.initUser(handle)
        viewModel.getContestProgressObservable().observe(this, Observer<Boolean> { progress ->
            if (progress) {
                loadingScreen!!.show()
            } else {
                loadingScreen!!.dismiss()
            }
        })
        viewModel.getUpComingContests().observe(this, Observer<List<Any?>?> { c ->
            adapter.updateList(c)
            contests = viewModel.getContestList().getValue()
        })
        viewModel.getUsersProfile().observe(this, Observer<Any?> { userResponse ->
            if (userResponse != null) {
                status = userResponse.getStatus()
                comment = userResponse.getComment()
                user = userResponse.getUserList().get(0)
                displayUserData(user)
            }
        })
    }

    private fun loadLoadingDialog() {
        val alertDialog = AlertDialog.Builder(this)
        val view: View = getLayoutInflater().inflate(R.layout.loading, null)
        alertDialog.setView(view)
        loadingScreen = alertDialog.create()
        loadingScreen.setCanceledOnTouchOutside(false)
    }

    fun initViews() {
        val searchUser: ExtendedFloatingActionButton =
            findViewById<ExtendedFloatingActionButton>(R.id.search_user)
        ratingProgress = findViewById<LinearProgressIndicator>(R.id.user_rating_progress)
        profilePic = findViewById<CircleImageView>(R.id.profile_photo)
        fullName = findViewById<TextView>(R.id.full_name)
        userName = findViewById<TextView>(R.id.user_name)
        userRank = findViewById<TextView>(R.id.user_rank)
        userRating = findViewById<TextView>(R.id.user_rating)
        val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.upcoming_contest_list)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(this))
        adapter = ContestAdapter(this@MainActivity, true)
        recyclerView.setAdapter(adapter)
        findViewById<View>(R.id.btn_more_user).setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, UserStatsActivity::class.java)
            intent.putExtra("userProfile", user)
            startActivity(intent)
        })
        findViewById<View>(R.id.btn_more_contest).setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, ContestActivity::class.java)
            intent.putParcelableArrayListExtra("contests", contests)
            startActivity(intent)
        })
        searchUser.setOnClickListener(View.OnClickListener { inflateThat() })
    }

    private fun inflateThat() {
        val alertDialog = AlertDialog.Builder(this)
        val view: View = getLayoutInflater().inflate(R.layout.search_user, null)
        alertDialog.setView(view)
        val inputLayout: TextInputLayout = view.findViewById<TextInputLayout>(R.id.search_edittext)
        val searchBtn: MaterialButton = view.findViewById<MaterialButton>(R.id.btn_search_user)
        searchBtn.setOnClickListener(View.OnClickListener {
            val s: String =
                Objects.requireNonNull<EditText>(inputLayout.getEditText()).getText().toString()
                    .trim { it <= ' ' }
            if (s.length == 0) {
                inputLayout.setError("input is empty")
                return@OnClickListener
            }
            viewModel.initUser(s)
        })
        val dialog = alertDialog.create()
        dialog.show()
        viewModel.isValidUser().observe(this, Observer<Boolean> { isValid ->
            if (Objects.requireNonNull<EditText>(inputLayout.getEditText()).getText().toString()
                    .trim { it <= ' ' }.length > 0
            ) {
                if (isValid) {
                    println("VALID")
                    handle = inputLayout.getEditText().getText().toString()
                    val editor: SharedPreferences.Editor = preferences.edit()
                    editor.putString("handle", handle)
                    editor.apply()
                    dialog.dismiss()
                } else {
                    println("INVALID")
                    inputLayout.setError("error please check if connected to internet or user handle is valid")
                }
            }
        })
    }

    fun displayUserData(user: User?) {
        Glide.with(this)
            .load(user!!.avatar)
            .into(profilePic)
        profilePic.setBorderColor(
            getResources().getColor(
                Utils.getRankColor(
                    user.rating
                )
            )
        )
        userName.setText(user.handle)
        userRank.setText(user.rank)
        userRank.setTextColor(
            getResources().getColor(
                Utils.getRankColor(
                    user.rating
                )
            )
        )
        userRating.setText("Rating: " + user.rating)
        val name = StringBuilder()
        if (user.firstName != null) {
            name.append(user.firstName)
            name.append(" ")
        }
        if (user.lastName != null) {
            name.append(user.lastName)
        }
        if (name.length == 0) fullName.setVisibility(View.GONE) else {
            fullName.setVisibility(View.VISIBLE)
            fullName.setText(name)
        }
        ratingProgress.setProgress(user.rating)
    }
}