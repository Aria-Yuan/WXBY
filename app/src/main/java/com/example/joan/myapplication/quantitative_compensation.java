package com.example.joan.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class quantitative_compensation extends AppCompatActivity {

    BarChart BarChart1;
    BarChart BarChart2;
    BarChart BarChart3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quantitative_compensation);

        BarChart1=(BarChart) findViewById(R.id.compensationGraph);

        ArrayList<BarEntry> barEntries1= new ArrayList<>();
        barEntries1.add(new BarEntry(44f,0));
        barEntries1.add(new BarEntry(32f,1));
        barEntries1.add(new BarEntry(23f,2));

        BarChart1.setDrawValueAboveBar(true);


        BarChart2=(BarChart) findViewById(R.id.sentencingGraph);

        ArrayList<BarEntry> barEntries2= new ArrayList<>();
        barEntries2.add(new BarEntry(12f,0));
        barEntries2.add(new BarEntry(6f,1));
        barEntries2.add(new BarEntry(23f,2));

        BarChart2.setDrawValueAboveBar(true);

        BarChart3=(BarChart) findViewById(R.id.interestGraph);

        ArrayList<BarEntry> barEntries3= new ArrayList<>();
        barEntries3.add(new BarEntry(44f,0));
        barEntries3.add(new BarEntry(24f,1));
        barEntries3.add(new BarEntry(5f,2));

        BarChart3.setDrawValueAboveBar(true);
    }
}
