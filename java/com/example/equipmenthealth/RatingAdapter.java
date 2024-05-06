package com.example.equipmenthealth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.RatingViewHolder> {

    private List<Rating> ratings;
    Context context;
    DBHelper dbHelper;

    public RatingAdapter(Context context, List<Rating> ratings) {
        this.context = context;
        this.ratings = ratings;
    }

    @NonNull
    @Override
    public RatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rating_card, parent, false);
        return new RatingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingViewHolder holder, int position) {
        Rating rating = ratings.get(position);
        dbHelper = new DBHelper(context);
        String username = dbHelper.getUsernameByUserId(rating.getUserId());
        holder.textViewUsername.setText(username);
        holder.ratingBar.setRating(rating.getRating());
    }

    @Override
    public int getItemCount() {
        return ratings.size();
    }

    static class RatingViewHolder extends RecyclerView.ViewHolder {
        TextView textViewUsername;
        RatingBar ratingBar;

        RatingViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUsername = itemView.findViewById(R.id.ep1);
            ratingBar = itemView.findViewById(R.id.ratingBar_employee1);
        }
    }
}

