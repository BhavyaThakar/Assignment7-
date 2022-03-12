package com.example.assignment7;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LocationRVAdapter extends RecyclerView.Adapter<LocationRVAdapter.LocationHolder>{
    ArrayList<MClass> locationData;
    Context context;

    public LocationRVAdapter(ArrayList<MClass> locationData, Context context) {
        this.locationData = locationData;
        this.context = context;
    }

    @NonNull
    @Override
    public LocationRVAdapter.LocationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View myOwnView = mInflater.inflate(R.layout.raw_location, parent, false);
        return  new LocationHolder(myOwnView);
    }

    @Override
    public int getItemCount() {
        return locationData.size();
    }

    public class LocationHolder extends RecyclerView.ViewHolder {
        TextView showLatitude, showLongitude, showCity, showAddress;
        Button delete;
        public LocationHolder(@NonNull View itemView) {
            super(itemView);
            showLatitude = itemView.findViewById(R.id.show_latitude_tv);
            showLongitude = itemView.findViewById(R.id.show_longitude_tv);
            showCity = itemView.findViewById(R.id.show_city_tv);
            showAddress = itemView.findViewById(R.id.show_address_tv);
            delete = itemView.findViewById(R.id.delete_entry);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull LocationRVAdapter.LocationHolder holder, int position) {

        MClass data = locationData.get(position);
        holder.showLatitude.setText(locationData.get(position).getLatitude());
        holder.showLongitude.setText(locationData.get(position).getLongitude());
        holder.showCity.setText(locationData.get(position).getLocality());
        holder.showAddress.setText(locationData.get(position).getAddress());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ForecastActivity.class);
                intent.putExtra("city", data.getLocality());
                context.startActivity(intent);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Location");
                builder.setMessage("Are you sure you want to Delete :  \n" + data.getAddress()+ " ?");
                builder.setIcon(android.R.drawable.ic_menu_delete);
                builder.setCancelable(false);
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHandler dbHandler = new DatabaseHandler(context);
                        dbHandler.deleteLocation(data.getAddress());
                        locationData.remove(data);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
            }
        });


    }




}
