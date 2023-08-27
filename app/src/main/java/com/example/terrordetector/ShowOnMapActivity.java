package com.example.terrordetector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class ShowOnMapActivity extends AppCompatActivity {
private Button btn_back;
private String userID;
private ArrayList <Location >arraylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_on_map);
        Bundle extras = getIntent().getExtras();
        userID = extras.getString("userID");
        arraylist = extras.getParcelableArrayList("arraylist");
        btn_back= findViewById(R.id.back_to_main);
        for (int i =0; i<arraylist.size();i++){
            Log.d("ShowOnMapActivity", String.valueOf(arraylist.get(i).getLatitude() + arraylist.get(i).getLatitude()));
        }



        Fragment fragment = new Map_Fragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("locations", arraylist);

// Set the arguments on the fragment
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).commit();



        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent myIntent =  new Intent(ShowOnMapActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userID", userID);
                myIntent.putExtras(bundle);

                startActivity(myIntent);
                finish();


            }
        });


    }

}