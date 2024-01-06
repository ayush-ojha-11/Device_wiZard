package com.as.devicewizard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeviceListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    MobileDeviceAdapter mobileDeviceAdapter;

    List<MobileDevice> mobileDeviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        recyclerView = findViewById(R.id.recycler_view_deviceList);
        databaseReference = FirebaseDatabase.getInstance().getReference("/categories/low_budget/phones");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mobileDeviceList = new ArrayList<>();
        mobileDeviceAdapter = new MobileDeviceAdapter(this,mobileDeviceList);
        recyclerView.setAdapter(mobileDeviceAdapter);

        mobileDeviceAdapter.notifyDataSetChanged();


        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    MobileDevice device = dataSnapshot.getValue(MobileDevice.class);

                    assert device != null;
                    String image = device.getImage_url();
                    String name = device.getName();
                    String processor = device.getProcessor();
                    String price = device.getPrice();
                    String store= device.getStore_url();
                    mobileDeviceList.add(new MobileDevice(name,processor,price,image,store));
                }
                mobileDeviceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(valueEventListener);
    }
}