package com.example.soil.charttest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class first_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        LineChart chart = (LineChart) findViewById(R.id.chart) ;

        String dataset_label1 = "Company 1";
        ArrayList<Entry> yVals1 = new ArrayList<>();
        yVals1.add(new Entry(100, 0));
        yVals1.add(new Entry(105, 1));
        yVals1.add(new Entry(100, 2));
        yVals1.add(new Entry(250, 3));
        LineDataSet dataSet1 = new LineDataSet(yVals1, dataset_label1);
        dataSet1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        dataSet1.setLineWidth(20);
        dataSet1.setCircleSize(10);
        dataSet1.setValueTextSize(15);

        String dataset_label2 = "Company 2";
        ArrayList<Entry> yVals2 = new ArrayList<>();
        int[] a ={10,20,100,200}  ;

        for(int i=0;i<a.length;i++){

            yVals2.add(new Entry(a[i], i));

        }

        LineDataSet dataSet2 = new LineDataSet(yVals2, dataset_label2);
        dataSet2.setLineWidth(100);
        dataSet2.setCircleSize(10);
        dataSet2.setValueTextSize(15);

        List<LineDataSet> dataSetList = new ArrayList<>();
        dataSetList.add(dataSet1);
        dataSetList.add(dataSet2);

        List<String> xVals = new ArrayList<>();
        for(int i=0;i<a.length;i++){
            xVals.add("Q"+i);
        }


        LineData data = new LineData(xVals, dataSetList);
        chart.setData(data);
        chart.invalidate();

    }
}
