package com.example.terrordetector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://terrordetector-default-rtdb.firebaseio.com");

    private Button btnBack;
private RecyclerView main_LST_result;
private String userID;
    private ArrayList<Result> arrayList_Result = new ArrayList<Result>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        userID = getIntent().getExtras().getString("userID");

        initViews();

      //  sendUserTheNewResultAndSave(userID,"Feace222book","TELkAVIV","234m","100","hiiii","c","C");


        databaseReference.child("History").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dss: snapshot.getChildren()){
                    Result r = dss.getValue(Result.class);
                    arrayList_Result.add(r);
                    MyAdapter adapter_Result = new MyAdapter(HistoryActivity.this,arrayList_Result);
                    main_LST_result.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
                    main_LST_result.setAdapter(adapter_Result);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = null;
                Bundle bundle = new Bundle();
                bundle.putString("alertID", userID);
                myIntent.putExtras(bundle);
                myIntent= new Intent(HistoryActivity.this, MainActivity.class);
                startActivity(myIntent);
                finish();
            }
        });

    }

    private void initViews() {
        main_LST_result  =findViewById(R.id.main_LST_result);
        btnBack = findViewById(R.id.btn_back);
    }
    private void sendUserTheNewResultAndSave(String alertID,String website,String location,String timestamp,String feedback,String text,String publisher,String keywords){

        databaseReference.child("History").child(alertID).addListenerForSingleValueEvent(new ValueEventListener(){

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            ResultPerUserID resultPerUserID = new ResultPerUserID(userID);
                            ArrayList <Result> arrayList =  new ArrayList<Result>();
                            for (DataSnapshot dss : snapshot.getChildren()){
                                Result r1= dss.getValue(Result.class);
                                arrayList.add(r1);
                            }
                            //get from the service the new result
                            arrayList.add(new Result(alertID,website,location,timestamp,feedback,text,publisher,keywords));
                            resultPerUserID.setResultArrayList(arrayList);
                            databaseReference.child("History").child(userID).setValue(arrayList).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"Arraylist is uploaded",Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        }
                        else{
                            ResultPerUserID resultPerUserID = new ResultPerUserID(userID);
                            ArrayList <Result> array= resultPerUserID.addResultToArray(alertID,website,location,timestamp,feedback,text,publisher,keywords);
                            databaseReference.child("History").child(userID).setValue(array).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"Arraylist is uploaded",Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}