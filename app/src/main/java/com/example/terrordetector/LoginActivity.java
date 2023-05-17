package com.example.terrordetector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Base64;

public class LoginActivity extends AppCompatActivity {
    EditText infousername, infoPassword;
    Button btnNextActivity;
    String name, password;
    private FirebaseAuth mAuth;
    private String nameEncoded, passEncoded;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://terrordetector-default-rtdb.firebaseio.com");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        initViews();
        btnNextActivity.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

            pressOnBTN();




    }
});


    }


    private String encodedString(String str){
        String encodedString = Base64.getEncoder().encodeToString(str.getBytes());
        Log.d("TAG", "encodedString: "+encodedString);
        return  encodedString;

    }

    private void sendUserSave(String name,String nameE,String pass){
        databaseReference.child("users").child(nameE).child("fullname").setValue(nameE);
        databaseReference.child("users").child(nameE).child("password").setValue(pass);
        databaseReference.child("users").child(nameE).child("phoneNumber").setValue("0505252055");

    }
    private boolean toCheck(String nameE, String name, String passE,String pass){
        return nameE.equals(name)&& passE.equals(pass);

    }


    private void pressOnBTN() {
        name = infousername.getText().toString();
        password = infoPassword.getText().toString();
        nameEncoded = encodedString(name);
        passEncoded =  encodedString(password);


        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(nameEncoded)){
                    final String getName = snapshot.child(nameEncoded).child("fullname").getValue(String.class);
                    final String getPassword = snapshot.child(nameEncoded).child("password").getValue(String.class);
                    final String getPhoneNumber = snapshot.child(nameEncoded).child("phoneNumber").getValue(String.class);
                    boolean ok = toCheck(nameEncoded,getName,passEncoded,getPassword);
                    if(ok==true){
                        Intent myIntent = new Intent(LoginActivity.this, loginActivityStep2.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("userID",getName);
                        bundle.putString("phoneNumber",getPhoneNumber);

                        myIntent.putExtras(bundle);

                        startActivity(myIntent);
                        finish();
                    }


                }
                else {

                    Toast.makeText(LoginActivity.this,"Unregistered user",Toast.LENGTH_SHORT).show();
                    //need to del
                    sendUserSave(name,nameEncoded,passEncoded);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    private void initViews() {
        infousername = findViewById(R.id.et_username);
        infoPassword = findViewById(R.id.et_password);
        btnNextActivity= findViewById(R.id.btn_login);
    }
}