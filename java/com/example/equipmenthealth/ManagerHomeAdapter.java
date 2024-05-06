package com.example.equipmenthealth;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.equipmenthealth.R;

import java.util.ArrayList;
import java.util.List;

public class ManagerHomeAdapter extends RecyclerView.Adapter<ManagerHomeAdapter.ViewHolder> {
    private List<User> managerList;
    private Context context;

    public ManagerHomeAdapter(Context context, List<User> managerList) {
        this.context = context;
        this.managerList = managerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manager_home_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User currentManager = managerList.get(position);
        holder.studentName.setText(currentManager.getUsername());
        holder.radioBtn.setVisibility(View.GONE);

        DBHelper dbHelper = new DBHelper(context);
        int userId = currentManager.getId();
        int collegeId = dbHelper.getAssignedCollegeId(userId);

        Log.d("collegeId", collegeId+"");

        if (collegeId != -1) {
            College college = dbHelper.getCollegeById(collegeId);
            if (college != null) {
                holder.collegeName.setText(college.getName());
                holder.imgBtn.setVisibility(View.GONE);
            } else {
                holder.imgBtn.setVisibility(View.VISIBLE);
                holder.collegeName.setVisibility(View.GONE);
            }
        } else {
            holder.collegeName.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return managerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView collegeName;
        TextView studentName;

        RadioButton radioBtn;
        ImageButton imgBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            collegeName = itemView.findViewById(R.id.collegeNameTV);
            studentName = itemView.findViewById(R.id.studentNametv);
            radioBtn = itemView.findViewById(R.id.rbtn);
            imgBtn = itemView.findViewById(R.id.imbtn);

            imgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCollegeSelectionDialog();
                }
            });
        }
        private void showCollegeSelectionDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
            builder.setTitle("Select College");

            DBHelper dbHelper = new DBHelper(itemView.getContext());
            final List<College> colleges = dbHelper.getAllColleges().subList(1, dbHelper.getAllColleges().size());
            List<String> collegeNames = new ArrayList<>();
            for (College college : colleges) {
                collegeNames.add(college.getName());
            }

            builder.setItems(collegeNames.toArray(new String[0]), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    College selectedCollege = colleges.get(which);

                    int collegeId = selectedCollege.getCollegeId(); // Assuming collegeId can be retrieved from College object
                    User currentUser = managerList.get(getAdapterPosition());
                    int userId = currentUser.getId(); // Retrieve userId from User object
                    boolean x = addAssignedCollege(userId, collegeId);

                    if(x) {
                        collegeName.setVisibility(View.VISIBLE);
                        collegeName.setText(selectedCollege.getName());
                        imgBtn.setVisibility(View.GONE);
                    }
                }
            });


            builder.show();
        }

        private boolean addAssignedCollege(int userId, int collegeId) {
            DBHelper dbHelper = new DBHelper(itemView.getContext());
            boolean result = dbHelper.addAssignedCollege(userId, collegeId);
            if (result) {
                Toast.makeText(itemView.getContext(), "College assigned successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(itemView.getContext(), "Failed to assign college", Toast.LENGTH_SHORT).show();
            }

            return result;
        }
    }
}
