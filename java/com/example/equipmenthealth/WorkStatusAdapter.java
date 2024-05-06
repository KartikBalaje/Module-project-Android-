package com.example.equipmenthealth;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class WorkStatusAdapter extends RecyclerView.Adapter<WorkStatusAdapter.WorkStatusViewHolder> {

    private static List<WorkStatusModel> workStatusList;
    Context context;


    public WorkStatusAdapter(List<WorkStatusModel> workStatusList, Context c) {
        this.workStatusList = workStatusList;
        this.context = c;
    }

    @NonNull
    @Override
    public WorkStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.work_status_card, parent, false);
        return new WorkStatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkStatusViewHolder holder, int position) {
        WorkStatusModel workStatus = workStatusList.get(position);
        holder.eqname.setText(workStatus.getEquipmentName());
        holder.id.setText("Id: " + workStatus.getId());
        holder.notworking.setText("Status: "+workStatus.getStatus());
        holder.biolab.setText("Lab: "+workStatus.getLab());
        holder.date.setText("Date: " + workStatus.getDate());
        holder.tcname.setText("Technician: " + workStatus.getTechnicianName());
        holder.jd.setText("Job Id: " + workStatus.getJobId());
    }

    @Override
    public int getItemCount() {
        return workStatusList.size();
    }

    static class WorkStatusViewHolder extends RecyclerView.ViewHolder {
        TextView eqname, id, notworking, biolab, date, time, tcname, jd;
        ImageView imbtn;

        DBHelper dbHelper;
        WorkStatusViewHolder(@NonNull View itemView) {
            super(itemView);
            eqname = itemView.findViewById(R.id.eqname);
            id = itemView.findViewById(R.id.id);
            notworking = itemView.findViewById(R.id.notworking);
            biolab = itemView.findViewById(R.id.biolab);
            date = itemView.findViewById(R.id.date);
            tcname = itemView.findViewById(R.id.tcname);
            jd = itemView.findViewById(R.id.jd);
            imbtn = itemView.findViewById(R.id.imbtn);

            SharedPreferences sf = itemView.getContext().getSharedPreferences("UserDetails", itemView.getContext().MODE_PRIVATE);

            imbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTechnicianSelectionDialog();
                }
            });
            if(sf.getString("designation", "").equals("technician")) {
                tcname.setVisibility(View.GONE);
                imbtn.setVisibility(View.GONE);
            }

        }

        private void showTechnicianSelectionDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
            builder.setTitle("Select Technician");

            dbHelper = new DBHelper(itemView.getContext());
            WorkStatusModel currentIssue = workStatusList.get(getAdapterPosition());

            if(currentIssue.getStatus().equals("Completed")) {
                builder.setTitle("Rate Technician");

                // Inflate a layout containing a RatingBar
                View view = LayoutInflater.from(itemView.getContext()).inflate(R.layout.rating_card, null);
                TextView tv = view.findViewById(R.id.ep1);
                tv.setText(currentIssue.getTechnicianName());
                RatingBar ratingBar = view.findViewById(R.id.ratingBar_employee1);
                builder.setView(view);

                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        float rating = ratingBar.getRating();
                        dbHelper.insertOrUpdateRating(dbHelper.getUserIdByUsername(currentIssue.getTechnicianName()), rating);
                        Toast.makeText(itemView.getContext(), "Submitted Succesfully", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            } else {

                List<User> users = dbHelper.getUsersByDesignation("technician");
                final String[] technicianNames = new String[users.size()];
                final int[] technicianIds = new int[users.size()];

                for (int i = 0; i < users.size(); i++) {
                    technicianNames[i] = users.get(i).getUsername();
                    technicianIds[i] = users.get(i).getId();
                }

                builder.setItems(technicianNames, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int selectedTechnicianId = technicianIds[which];
                        tcname.setText("Technician: " + technicianNames[which]);
                        updateIssueAssignedTo(Integer.parseInt(currentIssue.getJobId()), selectedTechnicianId);
                    }
                });
                builder.show();
            }
        }
        private void updateIssueAssignedTo(int issueId, int selectedTechnicianId) {
            dbHelper.updateIssueAssignedTo(issueId, selectedTechnicianId);
        }
    }
}
