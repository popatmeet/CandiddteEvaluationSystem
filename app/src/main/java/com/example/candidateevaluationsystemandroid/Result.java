package com.example.candidateevaluationsystemandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Result extends AppCompatActivity {

    FirebaseUser user;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        PieChart pieChart = (PieChart) findViewById(R.id.chart);
        List<PieEntry> trueAns = new ArrayList<>();
        trueAns.add(new PieEntry(getIntent().getIntExtra("AppScore",1),0));
        trueAns.add(new PieEntry(10 - getIntent().getIntExtra("AppScore",1),1));
        PieDataSet dataSet = new PieDataSet(trueAns,"Correct Answers");
        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.animateXY(2000,2000);

       //   Map<String, Object> result= new HashMap<>();
      //  result.put("Result",getIntent().getIntExtra("AppScore",1));

        String sco = getIntent().getStringExtra("monum");
        //Toast.makeText(getApplicationContext(),"result " +user.getPhoneNumber(),Toast.LENGTH_LONG).show();
       db.collection("Resumes").document(sco).update("Result",""+getIntent().getIntExtra("AppScore",1));

    }
}
