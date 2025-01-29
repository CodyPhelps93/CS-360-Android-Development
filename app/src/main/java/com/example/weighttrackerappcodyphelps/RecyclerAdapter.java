package com.example.weighttrackerappcodyphelps;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private final Context context;
    private final ArrayList<String> userId;
    private final ArrayList<String> currentWeight;
    private final ArrayList<String> previousWeight;
    private final ArrayList<String> currDate;
    private final ArrayList<String> goal;
    private  int selectedItemPosition = RecyclerView.NO_POSITION;

    public void setSelectedItemPosition(int position) {
        selectedItemPosition = position;
        if (position != RecyclerView.NO_POSITION && position < userId.size()) {
            int itemId = Integer.parseInt(userId.get(position)); // Convert userId to int
            selectedItemPosition = itemId; // Set position as itemId
        }
    }

    public int getSelectedItemPosition(){
        return selectedItemPosition;
    }

    RecyclerAdapter(Context context, ArrayList<String> userId, ArrayList<String> currentWeight, ArrayList<String> previousWeight, ArrayList<String> currDate,
                    ArrayList<String> goal){
        this.context = context;
        this.userId = userId;
        this.currentWeight = currentWeight;
        this.previousWeight = previousWeight;
        this.currDate = currDate;
        this.goal = goal;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycle_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.userIdTxt.setText(userId.get(position));
        holder.currentWeightTxt.setText(currentWeight.get(position));
        holder.previousWeightTxt.setText(previousWeight.get(position));
        holder.currDateTxt.setText(currDate.get(position));
        holder.goalWeightTxt.setText(goal.get(position));

        int itemId = Integer.parseInt(userId.get(position)); // Assuming userId holds the ID as a String

        // Highlight the selected item
        if (itemId == selectedItemPosition) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_blue_light));
            holder.userIdTxt.setTextColor(ContextCompat.getColor(context, android.R.color.black));
            holder.currentWeightTxt.setTextColor(ContextCompat.getColor(context, android.R.color.black));
            holder.previousWeightTxt.setTextColor(ContextCompat.getColor(context, android.R.color.black));
            holder.currDateTxt.setTextColor(ContextCompat.getColor(context, android.R.color.black));
            holder.goalWeightTxt.setTextColor(ContextCompat.getColor(context, android.R.color.black));
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
            holder.userIdTxt.setTextColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark));
            holder.currentWeightTxt.setTextColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark));
            holder.previousWeightTxt.setTextColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark));
            holder.currDateTxt.setTextColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark));
            holder.goalWeightTxt.setTextColor(ContextCompat.getColor(context,android.R.color.holo_blue_dark));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedItemPosition(itemId);
                notifyDataSetChanged(); // Refresh the RecyclerView to apply the new selection
            }
        });
    }

    @Override
    public int getItemCount() {
        return userId.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView userIdTxt, currentWeightTxt, previousWeightTxt, currDateTxt, goalWeightTxt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userIdTxt = itemView.findViewById(R.id.weightIDText);
            currentWeightTxt = itemView.findViewById(R.id.recycleCurrWeight);
            previousWeightTxt = itemView.findViewById(R.id.recyclePrevWeight);
            currDateTxt = itemView.findViewById(R.id.recycleDate);
            goalWeightTxt = itemView.findViewById(R.id.recycleGoalWeight);
        }
    }


}
