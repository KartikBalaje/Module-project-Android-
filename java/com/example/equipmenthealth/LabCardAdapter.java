package com.example.equipmenthealth;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LabCardAdapter extends RecyclerView.Adapter<LabCardAdapter.ViewHolder> {
    private final List<Lab> labList;
    private final Context context;
    private List<Lab> filteredList;


    public LabCardAdapter(Context context, List<Lab> labList) {
        this.context = context;
        this.labList = labList;
        this.filteredList = new ArrayList<>(labList);

    }


    @NonNull
    @Override
    public LabCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lab_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LabCardAdapter.ViewHolder holder, int position) {
        Lab currentLab = filteredList.get(position);
        holder.labname.setText(currentLab.getLabName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EqupimentList.class);
                intent.putExtra("labId", currentLab.getLabId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }
    public void filter(String searchText) {
        filteredList.clear();
        if (searchText.isEmpty()) {
            filteredList.addAll(labList); // If search text is empty, show all items
        } else {
            String query = searchText.toLowerCase().trim();
            for (Lab lab : labList) {
                if (lab.getLabName().toLowerCase().contains(query)) {
                    filteredList.add(lab); // Add items that match the search query
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView labname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            labname = itemView.findViewById(R.id.idTVCardName);
        }
    }
}
