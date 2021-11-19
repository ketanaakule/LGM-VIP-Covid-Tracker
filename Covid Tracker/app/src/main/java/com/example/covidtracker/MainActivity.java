package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.widget.EditText;
import android.widget.Switch;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CustomAdapter stateWiseAdapter;
    private ArrayList<Model> stateWiseModelArrayList;

    private String str_state, str_confirmed, str_active, str_recovered, str_death;

    AnyChartView anyChartView;
    String[] pieName = {"Active", "Recovered", "Death"};
    int[] pieData = {363849, 31441260, 432112};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anyChartView = findViewById(R.id.any_chart_view);

//        Initialising adapter
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        stateWiseModelArrayList = new ArrayList<>();
        stateWiseAdapter = new CustomAdapter(stateWiseModelArrayList);
        recyclerView.setAdapter(stateWiseAdapter);

        //Fetch Statewise data
        FetchStateWiseData();

        setPieChart();

    }

    public void setPieChart(){
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();

        for (int i=0; i<pieName.length; i++){
            dataEntries.add(new ValueDataEntry(pieName[i], pieData[i]));
        }

        pie.data(dataEntries);
        pie.animation(true, 1000);
        pie.title("Cases in India");
        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Total Cases: 3,22,49,900")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);
        anyChartView.setChart(pie);

    }

    private void FetchStateWiseData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String apiURL = "https://data.covid19india.org/data.json";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                apiURL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("statewise");
                            stateWiseModelArrayList.clear();

                            Model heading = new Model("State", "Total Cases", "Active Cases", "Recovered",
                                    "Death");
                            stateWiseModelArrayList.add(heading);

                            for (int i = 1; i < jsonArray.length() ; i++){
                                JSONObject statewise = jsonArray.getJSONObject(i);

                                //After fetching, storing the data into strings
                                str_state = statewise.getString("state");

                                str_confirmed = statewise.getString("confirmed");

                                str_active = statewise.getString("active");

                                str_recovered = statewise.getString("recovered");

                                str_death = statewise.getString("deaths");

                                //Creating an object of our statewise model class and passing the values in the constructor
                                Model stateWiseModel = new Model(str_state, str_confirmed, str_active, str_recovered,
                                        str_death);
                                //adding data to our arraylist
                                stateWiseModelArrayList.add(stateWiseModel);
                            }

                            Handler makeDelay = new Handler();
                            makeDelay.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    stateWiseAdapter.notifyDataSetChanged();
                                }
                            }, 1000);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

}