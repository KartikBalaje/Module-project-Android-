package com.example.equipmenthealth;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.ViewHolder> {
    private final List<Equipment> equipmentList;
    private List<Equipment> filteredList;
    private Context context;

    public EquipmentAdapter(Context context, List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
        this.filteredList = new ArrayList<>(equipmentList);
        this.context = context;
    }

    @NonNull
    @Override
    public EquipmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.equipment_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EquipmentAdapter.ViewHolder holder, int position) {
        Equipment currentEquipment = filteredList.get(position);
        holder.equipmentName.setText(currentEquipment.getName());
        holder.equipmentStatus.setText("Condition : "+currentEquipment.getStatus());
        holder.equipmentId.setText("TDS"+currentEquipment.getId());

        if(currentEquipment.getStatus().toLowerCase().equals("working")) {
            holder.green_dot.setImageResource(R.drawable.green_circle);
        } else {
            holder.green_dot.setImageResource(R.drawable.red_circle);
        }

        holder.view360Button.setVisibility(currentEquipment.isShowView360Button() ? View.VISIBLE : View.GONE);
        holder.equipmentId.setVisibility(currentEquipment.isShowView360Button() ? View.VISIBLE : View.GONE);

        holder.green_dot.setVisibility(currentEquipment.isShowView360Button() ? View.GONE : View.VISIBLE);
        holder.equipmentStatus.setVisibility(currentEquipment.isShowView360Button() ? View.GONE : View.VISIBLE);

        if(currentEquipment.getName().toLowerCase().contains("reader")) {
            holder.equipmentImg.setImageResource(R.drawable.reader);
        } else if(currentEquipment.getName().toLowerCase().contains("microscope")) {
            holder.equipmentImg.setImageResource(R.drawable.microscope);
        } else if(currentEquipment.getName().toLowerCase().contains("analyzer")) {
            holder.equipmentImg.setImageResource(R.drawable.analyzer);
        } else if(currentEquipment.getName().toLowerCase().contains("centrifuge")) {
            holder.equipmentImg.setImageResource(R.drawable.centrifuge);
        }

        holder.view360Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, EquipmentDetails.class);
                i.putExtra("name", currentEquipment.getName());
                i.putExtra("features", currentEquipment.getFeatures());
                i.putExtra("date_of_purchase", currentEquipment.getPurchaseDate());
                i.putExtra("services_undergone", currentEquipment.getServicesUndergone());
                i.putExtra("total_services", currentEquipment.getServicesUndergone());
                i.putExtra("price", currentEquipment.getPrice());
                i.putExtra("workingCondition", currentEquipment.getStatus());
                i.putExtra("eow", currentEquipment.getEndOfWarranty());
                context.startActivity(i);
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
            filteredList.addAll(equipmentList);
        } else {
            String query = searchText.toLowerCase().trim();
            for (Equipment equipment : equipmentList) {
                if (equipment.getName().toLowerCase().contains(query)) {
                    filteredList.add(equipment);
                }
            }
        }
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView equipmentName;
        TextView equipmentStatus;
        Button view360Button;
        ImageView equipmentImg;
        TextView equipmentId;
        ImageView green_dot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            equipmentName = itemView.findViewById(R.id.scopeTextView);
            equipmentStatus = itemView.findViewById(R.id.workingTextView);
            view360Button = itemView.findViewById(R.id.View360);
            equipmentImg = itemView.findViewById(R.id.equipmentImg);
            equipmentId = itemView.findViewById(R.id.equipmentId);
            green_dot = itemView.findViewById(R.id.green_dot);
        }
    }
}
