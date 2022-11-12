package com.example.terrordetector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class RegisterActivity extends AppCompatActivity {
    EditText infoMail, infoPassword, infoUserName;
    ImageButton btnNextActivity;
    Button btnForLofIn;
    String name, password,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();

        btnNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressOnBTN();
                Intent myIntent = null;
                myIntent= new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(myIntent);
            }
        });
btnForLofIn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent myIntent = null;
        myIntent= new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(myIntent);
    }
});

    }

    private void pressOnBTN() {
        name=infoUserName.getText().toString();
        password = infoPassword.getText().toString();
        email = infoMail.getText().toString();

    }

    private void initViews() {
        infoMail = findViewById(R.id.et_register_email);
        infoPassword = findViewById(R.id.et_register_password);
        infoUserName = findViewById(R.id.et_register_username);
        btnNextActivity = findViewById(R.id.btn_reg_ok);
        btnForLofIn = findViewById(R.id.btn_go_to_login_from_reg);

    }
}