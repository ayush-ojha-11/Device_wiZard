package com.as.devicewizard;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MobileDeviceAdapter extends RecyclerView.Adapter<MobileDeviceAdapter.DeviceViewHolder> {

    private Context context;
    private List<MobileDevice> deviceList;

    public MobileDeviceAdapter(Context context,List<MobileDevice> deviceList) {
        this.context = context;
        this.deviceList = deviceList;
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mobile_device_item, parent, false);
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        MobileDevice device = deviceList.get(position);
        Glide.with(context).load(device.getImage_url()).into(holder.deviceImageView);
        holder.deviceNameTextView.setText(device.getName());
        holder.deviceProcessorTextView.setText("Processor: " + device.getProcessor());
        holder.devicePriceTextView.setText("Price: ₹" + device.getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(device.getStore_url()));
                context.startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    public static class DeviceViewHolder extends RecyclerView.ViewHolder {
        ImageView deviceImageView;
        TextView deviceNameTextView;
        TextView deviceProcessorTextView;
        TextView devicePriceTextView;

        public DeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            deviceImageView = itemView.findViewById(R.id.deviceImageView);
            deviceNameTextView = itemView.findViewById(R.id.deviceNameTextView);
            deviceProcessorTextView = itemView.findViewById(R.id.deviceProcessorTextView);
            devicePriceTextView = itemView.findViewById(R.id.devicePriceTextView);
        }
    }
}
