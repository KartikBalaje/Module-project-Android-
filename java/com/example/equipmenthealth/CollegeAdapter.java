package com.example.equipmenthealth;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CollegeAdapter extends RecyclerView.Adapter<CollegeAdapter.ViewHolder> {
    private List<College> collegeList;
    private Context context;

    public CollegeAdapter(Context context, List<College> collegeList) {
        this.context = context;
        this.collegeList = collegeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.college_name_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        College college = collegeList.get(position);
        holder.collegeNameBtn.setText(college.getName());
        holder.collegeNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Labslist.class);
                i.putExtra("collegeId", college.getCollegeId());
                Log.d("Colllleggeee IDDD", ""+college.getCollegeId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return collegeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button collegeNameBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            collegeNameBtn = itemView.findViewById(R.id.collegeName);
        }
    }
}

