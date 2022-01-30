package com.ritesh.codeforcesportal.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.Renderer;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.ritesh.codeforcesportal.R;
import com.ritesh.codeforcesportal.model.Problem;
import com.ritesh.codeforcesportal.model.Submission;
import com.ritesh.codeforcesportal.model.User;
import com.ritesh.codeforcesportal.viewmodel.UserStatsViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserStatsActivity extends AppCompatActivity {

    private PieChart verdictChart;
    private BarChart ratingChart;
    private TextView userName, fullName, userRank, userRating;
    private CircleImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_stats);

        initViews();
        displayUserData(getIntent().getParcelableExtra("userProfile"));
        setPieChart();
        UserStatsViewModel viewModel = new ViewModelProvider(this).get(UserStatsViewModel.class);
        viewModel.init();
        viewModel.getUserSubmissions().observe(this, new Observer<List<Submission>>() {
            @Override
            public void onChanged(List<Submission> submissions) {
                if(submissions != null) {
                    createChartData(submissions);
                }
            }
        });
    }

    private void initViews() {
        verdictChart = findViewById(R.id.piechart);
        ratingChart = findViewById(R.id.barChart);
        profilePic = findViewById(R.id.profile_photo);
        fullName = findViewById(R.id.full_name);
        userName = findViewById(R.id.user_name);
        userRank = findViewById(R.id.user_rank);
        userRating = findViewById(R.id.user_rating);
    }

    public void displayUserData(User user) {
        Glide.with(this)
                .load(user.getAvatar())
                .into(profilePic);

        userName.setText(user.getHandle());
        userRank.setText(user.getRank());
        userRating.setText("Rating: " + user.getRating());
        StringBuilder name = new StringBuilder("");
        if(user.getFirstName() != null) {
            name.append(user.getFirstName());
            name.append(" ");
        }
        if(user.getLastName() != null) {
            name.append(user.getLastName());
        }
        if(name.length() == 0)
            fullName.setVisibility(View.GONE);
        else {
            fullName.setVisibility(View.VISIBLE);
            fullName.setText(name);
        }
    }

    private void createChartData(List<Submission> submissions) {
        HashMap<String,Float> verdictMap = new HashMap<>();
        HashMap<Integer,Float> ratingsMap = new HashMap<>();
//        final int count = submissions.size();
        for (Submission submission : submissions) {
            String verdict = submission.getVerdict();
            if(verdictMap.containsKey(verdict)) {
                verdictMap.put(verdict,verdictMap.get(verdict)+1f);
            } else {
                verdictMap.put(verdict,1f);
            }
            Problem problem = submission.getProblem();
            //WARNING: assume for now that rating is valid
            if(ratingsMap.containsKey(problem.getRating())) {
                ratingsMap.put(problem.getRating(),ratingsMap.get(problem.getRating())+1f);
            } else {
                ratingsMap.put(problem.getRating(), 1f);
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
//        verdictData.setValueFormatter(new PercentFormatter());
        verdictData.setValueTextSize(12f);
        verdictData.setValueTextColor(Color.BLACK);

        //Bar chart load
        BarDataSet set = new BarDataSet(ratingsList,"Ratings");
        set.setColors(colors);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);
        BarData data = new BarData(set);
//        data.setValueTextSize(12f);
        data.setBarWidth(60f);
        //Bar chart update
//        TODO: just look at bar chart and you will know
        ratingChart.setData(data);
        ratingChart.invalidate();
        ratingChart.animateY(1400);

        //pie chart update
        verdictChart.setData(verdictData);
        verdictChart.invalidate();
        verdictChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
    }

    private void setPieChart() {
        verdictChart.setDrawHoleEnabled(false);
        verdictChart.setUsePercentValues(true);
        verdictChart.setEntryLabelTextSize(12);
        verdictChart.setEntryLabelColor(Color.BLACK);
        verdictChart.getDescription().setEnabled(false);
        Legend l = verdictChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);

        ratingChart.setDrawBarShadow(false);
//        ratingChart.setDrawValueAboveBar(false);
        ratingChart.getDescription().setEnabled(false);
//        ratingChart.setPinchZoom(false);
        ratingChart.setDrawGridBackground(false);
        ratingChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);


    }
}