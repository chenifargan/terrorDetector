package com.example.terrordetector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoryActivity extends AppCompatActivity implements MyAdapter.OnRecordEventListener {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://terrordetector-default-rtdb.firebaseio.com");

    private Button btnBack;
private RecyclerView main_LST_result;
private String userID;
    private ArrayList<Result> arrayList_Result = new ArrayList<Result>();
    private  MyAdapter adapter_Result;
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
                    Log.d("TAG", r.getFeedback());
                    arrayList_Result.add(r);


                }
                adapter_Result = new MyAdapter(HistoryActivity.this,arrayList_Result,HistoryActivity.this);
                //main_LST_result.setLayoutManager(new GridLayoutManager(HistoryActivity.this,2));

                main_LST_result.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
                main_LST_result.setAdapter(adapter_Result);
//                adapter_Result.setResultItemClickListener(new MyAdapter.resultItemClickListener() {
//                    @Override
//                    public void favoriteClicked(Result result, int position,float rating) {
//
//                        result.setFeedback(String.valueOf(rating));
//                        Log.d("TAG", result.getFeedback());
//                        main_LST_result.getAdapter().notifyItemChanged(position);
//
//                    }
//                });





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8082/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        UpdateFeedback updateFeedback = retrofit.create(UpdateFeedback.class);
                Call<Integer> listCall = null;
        for (int i =0; i<arrayList_Result.size();i++) {

           listCall  =updateFeedback.updateFeedback(arrayList_Result.get(i).getAlertid(), arrayList_Result.get(i));
            listCall.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (!response.isSuccessful()) {

                        //   textView.setText("Code " + response.code());
                        return;
                    }


                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {

                }
            });
        }


                Intent myIntent = new Intent(HistoryActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                Log.d("TAG", "onClick: "+userID);
                bundle.putString("alertID", userID);
                myIntent.putExtras(bundle);
              //  myIntent=
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

    @Override
    public void onRatingBarChange(Result item, String value,int position) {

        item.setFeedback(value);
        Log.d("cheni", item.getFeedback());
        databaseReference.child("History").child(userID).child(String.valueOf(position)).child("feedback").setValue(value).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("TAG", "onSuccess: ");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", "onFailure: ");
            }
        });

//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://10.0.2.2:8082/")
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();
//        UpdateFeedback updateFeedback = retrofit.create(UpdateFeedback.class);
//        updateFeedback.updateFeedback(item.getAlertid(),item);




    }
}