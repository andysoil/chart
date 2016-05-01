package com.example.soil.charttest;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.data.Entry ;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button input ;
    EditText in_first,in_second ;
    RequestQueue requestQueue;
    private Calendar myCalendar,myCalendar2;
    public static final String TAG = "MyTag";

    public static String url  = "http://163.17.9.134/android_login_api/chart/chart_show.php" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = (Button)findViewById(R.id.input) ;
        in_first = (EditText)findViewById(R.id.first);
        in_second = (EditText)findViewById(R.id.second) ;
        myCalendar = Calendar.getInstance();
        myCalendar2 = Calendar.getInstance();

        in_first.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MainActivity.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        in_second.setOnClickListener(new View.OnClickListener() {
//日期事件
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MainActivity.this, date2, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        // Instantiate the RequestQueue.

        input.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String first = in_first.getText().toString().trim();
                String second = in_second.getText().toString().trim();
                inputdate(first, second);

            }
        });


    }
//設定時間
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();

        }

    };

    DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            // TODO Auto-generated method stub
            myCalendar2.set(Calendar.YEAR, year);
            myCalendar2.set(Calendar.MONTH, monthOfYear);
            myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel2();

        }

    };
    //更新時間
    private void updateLabel() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        in_first.setText(sdf.format(myCalendar.getTime()));
    }
    private void updateLabel2() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        in_second.setText(sdf.format(myCalendar2.getTime()));
    }

    private void inputdate(final String first, final String second){

        StringRequest checkreq = new StringRequest(Request.Method.POST, url , new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");
                            JSONArray dates = jObj.getJSONArray("dates");
                            JSONArray value = jObj.getJSONArray("value");

                            LineChart chart = (LineChart) findViewById(R.id.chart_one) ;
                            String dataset_label2 = "血糖";
                            ArrayList<Entry> yVals2 = new ArrayList<>();

                                int [] num =new int[value.length()] ;

                                for(int i =0;i<value.length();i++) {

                                   num[i] = Integer.parseInt(value.getString(i));
                                    Log.d(TAG, "NAME=" +  num[i]);
                                    yVals2.add(new Entry(num[i]  , i));
                                }
                            LineDataSet dataSet2 = new LineDataSet(yVals2, dataset_label2);
                            dataSet2.setLineWidth(100);
                            dataSet2.setCircleSize(10);
                            dataSet2.setValueTextSize(15);
                            List<LineDataSet> dataSetList = new ArrayList<>();
                            dataSetList.add(dataSet2);

                            List<String> xVals = new ArrayList<>();
                            for(int i=0;i<value.length();i++){
                                xVals.add(dates.getString(i));
                            }
                            LineData data = new LineData(xVals, dataSetList);
                            chart.setData(data);
                            chart.invalidate();

                        }catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> parmater = new HashMap<>() ;
                Log.d(TAG,"NAME="+first ) ;
                parmater.put("f_year",first) ;
                parmater.put("s_year", second) ;
                return  parmater ;
            }


        };
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(checkreq) ;
    }

    public void EditFirstButton(View view) {
    }
}



