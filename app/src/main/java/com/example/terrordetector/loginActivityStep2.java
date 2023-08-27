package com.example.terrordetector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class loginActivityStep2 extends AppCompatActivity {
EditText phoneNumber, code;
Button nextToGetCode, nextActivity;
Button notReciveCode;
private String phoneNumberstr,userID;
FirebaseAuth mAuth;
String verifcationID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_step2);
        userID = getIntent().getExtras().getString("userID");
        phoneNumberstr = getIntent().getExtras().getString("phoneNumber");

        initViews();





        

        nextToGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if(TextUtils.isEmpty(phoneNumberstr))
                {
                    Toast.makeText(loginActivityStep2.this,"Enter Valid Phone Number No.",Toast.LENGTH_SHORT).show();
                }
                else {
                    String number = phoneNumber.getText().toString();
                    sendverificationcode(phoneNumberstr);

                }



            }
        });
        nextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(code.getText().toString()))
                {
                    Toast.makeText(loginActivityStep2.this,"Wrong OTP Entered.",Toast.LENGTH_SHORT).show();
                }
                else{
                    verifyCode(code.getText().toString());

                }






            }
        });


    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifcationID,code);
        signInByCredentials(credential);
    }

    private void signInByCredentials(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(loginActivityStep2.this,"LogIn Successful",Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent(loginActivityStep2.this, MainActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("userID",userID);
                            myIntent.putExtras(bundle);

                            startActivity(myIntent);
                            finish();
                        }
                    }
                });
    }

    private void sendverificationcode(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+972"+phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

private PhoneAuthProvider.OnVerificationStateChangedCallbacks

    mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
            final String code = credential.getSmsCode();
            if(code!= null){

                verifyCode(code);
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Toast.makeText(loginActivityStep2.this,"Verification Failed",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s,
                @NonNull PhoneAuthProvider.ForceResendingToken token) {

            super.onCodeSent(s,token);
            verifcationID = s;
            Toast.makeText(loginActivityStep2.this,"Code sent",Toast.LENGTH_SHORT).show();
           // code.setEnabled(true);
            code.setVisibility(View.VISIBLE);
            nextToGetCode.setVisibility(View.INVISIBLE);
            nextActivity.setVisibility(View.VISIBLE);


        }
    };

    private void initViews() {
        phoneNumber =  findViewById(R.id.et_PhoneNumber);
        code = findViewById(R.id.et_Code);
        nextToGetCode =  findViewById(R.id.btn_next);
        notReciveCode = findViewById(R.id.btn_NotRecive);
        nextActivity = findViewById(R.id.btn_nextActivity);
        mAuth = FirebaseAuth.getInstance();
    }

}