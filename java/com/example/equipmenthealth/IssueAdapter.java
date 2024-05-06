package com.example.equipmenthealth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.ViewHolder> {

    private List<Issue> issues;
    Context context;

    public IssueAdapter(List<Issue> issues, Context context) {
        this.issues = issues;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.issue_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Issue issue = issues.get(position);
        // Bind issue data to ViewHolder views
        holder.issueIdTextView.setText("TD"+String.valueOf(issue.getIssueId()));
        // Set other views similarly

        SharedPreferences sf = context.getSharedPreferences("UserDetails", context.MODE_PRIVATE);
        String des = sf.getString("designation", "");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InProgress.class);
                intent.putExtra("issueId", issue.getIssueId());
                intent.putExtra("issueDate", issue.getIssueDate());
                intent.putExtra("equipmentId", issue.getEquipmentId());
                intent.putExtra("status", issue.getStatus());
                intent.putExtra("assignedTo", issue.getAssignedTo());
                intent.putExtra("description", issue.getDescription());
                intent.putExtra("labId", issue.getLabId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return issues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView issueIdTextView;
        // Other views

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            issueIdTextView = itemView.findViewById(R.id.issueIdTextView);
        }
    }
}

