package com.example.terrordetector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    ImageButton btn;
    EditText infoInsertWord,infoLocation,infoTime, infoConcentrationlevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toFind();
                Intent myIntent = null;
              //  myIntent= new Intent(MainActivity.this, LoginActivity.class);// activity google map
                startActivity(myIntent);
            }
        });

    }

    private void toFind() {

    }

    private void initViews() {
        infoInsertWord = findViewById(R.id.et_insertWord);
        infoLocation = findViewById(R.id.et_location);
        infoTime= findViewById(R.id.et_time);
        infoConcentrationlevel= findViewById(R.id.et_concentrationlevel);
        btn = findViewById(R.id.btn_find);
    }
}