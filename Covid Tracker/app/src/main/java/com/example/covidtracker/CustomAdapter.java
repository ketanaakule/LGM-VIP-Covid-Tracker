package com.example.covidtracker;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<Model> localDataSet;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     *                Step 1
     */
    public CustomAdapter(ArrayList<Model> dataSet) {
        localDataSet = dataSet;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_stateName, tv_stateTotalCases, tv_stateActiveCase, tv_stateRecovered, tv_stateDeath;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_stateName = itemView.findViewById(R.id.state);
            tv_stateTotalCases = itemView.findViewById(R.id.totalCase);
            tv_stateActiveCase = itemView.findViewById(R.id.active);
            tv_stateRecovered = itemView.findViewById(R.id.recovered);
            tv_stateDeath = itemView.findViewById(R.id.death);
        }
    }

    // Create new views (invoked by the layout manager) Step 2
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.testing, parent, false);
        return new ViewHolder(view);
    }


    // Replace the contents of a view (invoked by the layout manager) Step 3
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Model currentItem = localDataSet.get(position);
        String stateName = currentItem.getState();
        String stateTotal = currentItem.getConfirmed();
        String stateActive = currentItem.getActive();
        String stateRecovered = currentItem.getRecovered();
        String stateDeath = currentItem.getDeath();

        if (position==0){
            holder.tv_stateTotalCases.setTextSize(17);
            holder.tv_stateActiveCase.setTextSize(17);
            holder.tv_stateRecovered.setTextSize(14);
            holder.tv_stateDeath.setTextSize(17);
            holder.tv_stateName.setTextSize(18);
        }

        holder.tv_stateTotalCases.setText(stateTotal);
        holder.tv_stateActiveCase.setText(stateActive);
        holder.tv_stateRecovered.setText(stateRecovered);
        holder.tv_stateDeath.setText(stateDeath);
        holder.tv_stateName.setText(stateName);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet==null ? 0 : localDataSet.size();
    }
}
