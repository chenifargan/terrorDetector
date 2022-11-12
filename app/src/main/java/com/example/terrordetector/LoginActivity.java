package com.example.terrordetector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    EditText infousername, infoPassword;
    ImageButton btnNextActivity;
    String name, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        btnNextActivity.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

            pressOnBTN();

            Intent myIntent = null;
            myIntent= new Intent(LoginActivity.this, loginActivityStep2.class);
            startActivity(myIntent);


    }
});


    }

    private void pressOnBTN() {
        name = infousername.getText().toString();
        password = infoPassword.getText().toString();

        ////////הצפנה ושמירת נתונים

    }

    private void initViews() {
        infousername = findViewById(R.id.et_username);
        infoPassword = findViewById(R.id.et_password);
        btnNextActivity= findViewById(R.id.btn_login);
    }
}