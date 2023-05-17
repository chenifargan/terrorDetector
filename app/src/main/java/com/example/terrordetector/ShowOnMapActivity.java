package com.example.terrordetector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ShowOnMapActivity extends AppCompatActivity {
private Button btn_back;
private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_on_map);
        userID ="CHEN";// getIntent().getExtras().getString("alertID");
        btn_back= findViewById(R.id.back_to_main);

        Fragment fragment = new Map_Fragment();
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