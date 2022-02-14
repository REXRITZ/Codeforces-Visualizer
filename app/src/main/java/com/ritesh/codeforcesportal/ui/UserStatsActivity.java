package com.ritesh.codeforcesportal.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.LayoutTransition;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.material.button.MaterialButton;
import com.ritesh.codeforcesportal.R;
import com.ritesh.codeforcesportal.adapter.TagsAdapter;
import com.ritesh.codeforcesportal.adapter.UserAdapter;
import com.ritesh.codeforcesportal.model.Problem;
import com.ritesh.codeforcesportal.model.Submission;
import com.ritesh.codeforcesportal.model.Tags;
import com.ritesh.codeforcesportal.model.User;
import com.ritesh.codeforcesportal.utils.Utils;
import com.ritesh.codeforcesportal.viewmodel.UserStatsViewModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserStatsActivity extends AppCompatActivity {

    private PieChart verdictChart;
    private BarChart ratingChart;
    public int[] PASTEL_COLORS;
    private RecyclerView tagsRecyclerView, userRecyclerView;
    private TagsAdapter tagsAdapter;
    private UserAdapter userAdapter;
    private TextView userName,userRank;
    private CircleImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_stats);

        PASTEL_COLORS = getResources().getIntArray(R.array.pastel_colors);
        initViews();
        User user = getIntent().getParcelableExtra("userProfile");
        displayUserData(user);
        setPieChart();
        UserStatsViewModel viewModel = new ViewModelProvider(this).get(UserStatsViewModel.class);
        viewModel.init(user != null?user.getHandle():"");
        viewModel.getUserSubmissions().observe(this, new Observer<List<Submission>>() {
            @Override
            public void onChanged(List<Submission> submissions) {
                createChartData(submissions);
            }
        });
    }

    private void initViews() {
        userName = findViewById(R.id.userName);
        userRank = findViewById(R.id.userRank);
        imageView = findViewById(R.id.profile_photo);
        verdictChart = findViewById(R.id.piechart);
        ratingChart = findViewById(R.id.barChart);
        tagsRecyclerView = findViewById(R.id.tags_recyclerview);
        tagsRecyclerView.setNestedScrollingEnabled(false);
        tagsRecyclerView.setHasFixedSize(true);
        tagsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tagsAdapter = new TagsAdapter(this,PASTEL_COLORS);
        tagsRecyclerView.setAdapter(tagsAdapter);
        userRecyclerView = findViewById(R.id.user_info_recyclerview);
        userRecyclerView.setHasFixedSize(true);
        userRecyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        userRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(userRecyclerView.getContext(),
                layoutManager.getOrientation());
        userRecyclerView.addItemDecoration(dividerItemDecoration);
        userAdapter = new UserAdapter(this);
        userRecyclerView.setAdapter(userAdapter);

        ViewGroup root = findViewById(R.id.root);
        root.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        boolean[] clicked = new boolean[4];
        for(int i = 0; i < 4; ++i) clicked[i] = false;
        MaterialButton userExpand, pieChartExpand, barChartExpand, tagsExpand;
        userExpand = findViewById(R.id.user_expand);
        pieChartExpand = findViewById(R.id.pie_expand);
        barChartExpand = findViewById(R.id.bar_expand);
        tagsExpand = findViewById(R.id.tags_expand);
        userExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!clicked[0]) {
                    userRecyclerView.setVisibility(View.VISIBLE);
                    userExpand.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24),null);
                } else {
                    userRecyclerView.setVisibility(View.GONE);
                    userExpand.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_right_24),null);
                }
                clicked[0] = !clicked[0];
            }
        });
        pieChartExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!clicked[1]) {
                    verdictChart.setVisibility(View.VISIBLE);
                    pieChartExpand.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24),null);
                } else {
                    verdictChart.setVisibility(View.GONE);
                    pieChartExpand.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_right_24),null);
                }
                clicked[1] = !clicked[1];
            }
        });
        barChartExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!clicked[2]) {
                    ratingChart.setVisibility(View.VISIBLE);
                    barChartExpand.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24),null);
                } else {
                    ratingChart.setVisibility(View.GONE);
                    barChartExpand.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_right_24),null);
                }
                clicked[2] = !clicked[2];
            }
        });
        tagsExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!clicked[3]) {
                    tagsRecyclerView.setVisibility(View.VISIBLE);
                    tagsExpand.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24),null);
                } else {
                    tagsRecyclerView.setVisibility(View.GONE);
                    tagsExpand.setCompoundDrawablesWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_right_24),null);
                }
                clicked[3] = !clicked[3];
            }
        });
    }

    public void displayUserData(User user) {
        if (user == null) return;
        Glide.with(this)
                .load(user.getAvatar())
                .placeholder(R.drawable.ic_placeholder_24)
                .into(imageView);
        imageView.setBorderColor(getResources().getColor(Utils.getRankColor(user.getRating())));
        userName.setText(user.getHandle());
        userRank.setTextColor(getResources().getColor(Utils.getRankColor(user.getRating())));
        userRank.setText(user.getRank());

        //user data
        ArrayList<Pair<String,String>> userList = new ArrayList<>();
        userList.add(new Pair<>("Handle",user.getHandle()));
        userList.add(new Pair<>("Rank",user.getRank()));
        userList.add(new Pair<>("Rating",String.valueOf(user.getRating())));
        if(user.getFirstName() != null || user.getLastName() != null)
            userList.add(new Pair<>("Name",user.getFirstName() + " " +user.getLastName()));
        userList.add(new Pair<>("Max rank", user.getMaxRank()));
        userList.add(new Pair<>("Max rating",String.valueOf(user.getMaxRating())));
        userAdapter.updateList(userList);

    }

    private void createChartData(List<Submission> submissions) {
        Toast.makeText(this, "FFF", Toast.LENGTH_SHORT).show();
        if(submissions == null) return;
        Toast.makeText(this, "UUU", Toast.LENGTH_SHORT).show();
        HashMap<String,Float> verdictMap = new HashMap<>();
        HashMap<Integer,Float> ratingsMap = new HashMap<>();
        //tag -> {correct,total}
        Map<String, double[]> tagsSet = new HashMap<>();

        for (Submission submission : submissions) {
            String verdict = submission.getVerdict();
            if(verdictMap.containsKey(verdict)) {
                verdictMap.put(verdict,verdictMap.get(verdict)+1f);
            } else {
                verdictMap.put(verdict,1f);
            }
            Problem problem = submission.getProblem();
            //WARNING: assume for now that rating is valid
            if(verdict.equals("OK")) {
                if(ratingsMap.containsKey(problem.getRating())) {
                    ratingsMap.put(problem.getRating(),ratingsMap.get(problem.getRating())+1f);
                } else {
                    ratingsMap.put(problem.getRating(), 1f);
                }
            }
            for(String tag : problem.getTags()) {
                double[] val;
                if(tagsSet.containsKey(tag)) {
                    val = tagsSet.get(tag);
                    if(verdict.equals("OK"))
                        val[0] += 1;
                    val[1] += 1;
                } else {
                    val = new double[2];
                    val[0] = 1d;
                    val[1] = 1d;
                }
                tagsSet.put(tag,val);
            }
        }

        ArrayList<PieEntry> verdictsList = new ArrayList<>();
        ArrayList<BarEntry> ratingsList = new ArrayList<>();
        for(Map.Entry<String,Float> entry : verdictMap.entrySet()) {
            verdictsList.add(new PieEntry(entry.getValue(),entry.getKey()));
        }
        for(Map.Entry<Integer,Float> entry : ratingsMap.entrySet()) {
            ratingsList.add(new BarEntry(entry.getKey(),entry.getValue()));
        }
        loadPieChartData(verdictsList,ratingsList);

        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.applyPattern(".00");
        ArrayList<Tags> tagsList = new ArrayList<>();
        for(Map.Entry<String,double[]> entry : tagsSet.entrySet()) {
            addTagToList(entry,tagsList, decimalFormat);
        }
        tagsAdapter.updateList(tagsList);

    }

    private void addTagToList(Map.Entry<String,double[]> entry, ArrayList<Tags> tagsList, DecimalFormat decimalFormat) {
        String tag = entry.getKey();
        Double solved = entry.getValue()[0];
        Double total = entry.getValue()[1];
        Double percent = (solved / total) * 100d;
        tagsList.add(new Tags(tag, decimalFormat.format(percent), solved.intValue(), total.intValue()));
    }

    private void loadPieChartData(ArrayList<PieEntry> verdictsList, ArrayList<BarEntry> ratingsList) {
        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }
        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        //Pie chart load
        PieDataSet verdictDataSet = new PieDataSet(verdictsList, "");
        verdictDataSet.setColors(colors);
        PieData verdictData = new PieData(verdictDataSet);
        verdictData.setDrawValues(true);
        verdictData.setValueTextSize(12f);
        verdictData.setValueTextColor(Color.BLACK);
        verdictData.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int ind, ViewPortHandler viewPortHandler) {
                if(verdictsList != null && verdictsList.size() > 0 && value / verdictsList.size() < 0.15) {
                    PieEntry entry1 = (PieEntry) entry;
                    entry1.setLabel("");
                    return "";
                }
                return String.format("%.2f",value);
            }
        });
        //pie chart update
        verdictChart.setData(verdictData);
        verdictChart.invalidate();
        verdictChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        //Bar chart load
        BarDataSet set = new BarDataSet(ratingsList,"Ratings");
        set.setColors(colors);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);
        BarData data = new BarData(set);
        data.setBarWidth(50f);
        //Bar chart update
        ratingChart.setFitBars(true);
        ratingChart.setData(data);
        ratingChart.setPinchZoom(false);
        ratingChart.setDragEnabled(true);
        ratingChart.getAxisRight().setEnabled(false);
        ratingChart.getXAxis().setDrawGridLines(false);
        ratingChart.getXAxis().setAxisMinimum(100);
        ratingChart.getXAxis().setGranularity(400f);
        ratingChart.animateY(1400);
        ratingChart.setVisibleXRangeMaximum(1500);
        ratingChart.invalidate();
    }

    private void setPieChart() {
        verdictChart.setDrawHoleEnabled(false);
        verdictChart.setUsePercentValues(true);
        verdictChart.setEntryLabelTextSize(12);
        verdictChart.setEntryLabelColor(Color.BLACK);
        verdictChart.getDescription().setEnabled(false);
        Legend l = verdictChart.getLegend();
//        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);

        ratingChart.setDrawBarShadow(false);
//        ratingChart.setDrawValueAboveBar(false);
        ratingChart.getDescription().setEnabled(false);
//        ratingChart.setPinchZoom(false);
        ratingChart.setDrawGridBackground(false);
        ratingChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);


    }
}