package com.example.terrordetector;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ImageButton btn;
    EditText infoInsertWord,infoLocation,infoTime, infoWeb;
    private Button btnShowHistory;
    int hour, minute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();



        btnShowHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = null;
                myIntent= new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(myIntent);
            }
        });



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //popTimePicker();
                Intent myIntent = null;
              //  myIntent= new Intent(MainActivity.this, LoginActivity.class);// activity google map
                startActivity(myIntent);
            }
        });

    }

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
            hour = selectedHour;
            minute = selectedMinute;
            infoTime.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,minute));
            }
        };
        int style = AlertDialog.THEME_HOLO_DARK;

TimePickerDialog timePickerDialog = new TimePickerDialog(this,style, onTimeSetListener,hour,minute,true);
timePickerDialog.setTitle("Select time");
timePickerDialog.show();

    }




    private void initViews() {
        infoInsertWord = findViewById(R.id.et_insertWord);
        infoLocation = findViewById(R.id.et_location);
        infoTime= findViewById(R.id.et_time);
        infoWeb= findViewById(R.id.et_web);
        btn = findViewById(R.id.btn_find);
        btnShowHistory = findViewById(R.id.btn_ShowHistory);

    }
}