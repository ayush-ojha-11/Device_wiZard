package com.as.devicewizard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton chatScreenButton;
    EditText budgetEdittext;
    Spinner spinner;

    Button findButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        budgetEdittext= findViewById(R.id.budgetEditText);
        spinner = findViewById(R.id.spinner);
        findButton= findViewById(R.id.findPhonesButton);
        setSupportActionBar(toolbar);

        chatScreenButton = findViewById(R.id.chat_screenBtn);
        chatScreenButton.setOnClickListener(v -> {

            startActivity(new Intent(MainActivity.this, ChatScreenActivity.class));

        });

        //Spinner
        String[] data = {"Gaming", "Camera", "Entertainment", "All Rounder"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DeviceListActivity.class));
            }
        });
    }


}