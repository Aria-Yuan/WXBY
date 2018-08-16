package com.example.joan.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class similarCases extends AppCompatActivity {

    float percentage[]={40f,60f};
    String success[]={"勝率","敗率"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar_cases);

        setupPieChart();
    }

    private void setupPieChart() {
        //populating a list of PieEnntries
        List<PieEntry>pieEntries= new ArrayList<>();
        for(int i=0;i<percentage.length;i++){
            pieEntries.add(new PieEntry(percentage[i]));
        }

        PieDataSet dataset = new PieDataSet(pieEntries,"percentage");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataset);

        //get the chart
        PieChart chart =(PieChart) findViewById(R.id.chart);
        chart.setData(data);
        chart.animateY(1000);
        chart.invalidate();
        chart.setUsePercentValues(true);


    }

}
