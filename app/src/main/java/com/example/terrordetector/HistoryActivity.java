package com.example.terrordetector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HistoryActivity extends AppCompatActivity {
private Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initViews();


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = null;
                myIntent= new Intent(HistoryActivity.this, MainActivity.class);
                startActivity(myIntent);
                finish();
            }
        });

    }

    private void initViews() {
        btnBack = findViewById(R.id.btn_back);
    }
}