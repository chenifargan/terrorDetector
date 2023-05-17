package com.example.terrordetector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestsActivity extends AppCompatActivity {
private LottieAnimationView lottieAnimationView;
private String userID,keyword,time,date,location,website;
private ArrayList<Result> margeArrayList = new ArrayList<>();
    private ArrayList<Result> arrayList = new ArrayList<>();

DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://terrordetector-default-rtdb.firebaseio.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        userID ="Y2hlbg==";// getIntent().getExtras().getString("userID");
        keyword = getIntent().getExtras().getString("keyword");
       time = getIntent().getExtras().getString("time");
       date = getIntent().getExtras().getString("date");
       location = getIntent().getExtras().getString("location");
       website = getIntent().getExtras().getString("website");
        Log.d("CHEN111111", keyword);
        Log.d("CHEN111111", time);
        Log.d("CHEN111111", date);
        Log.d("CHEN111111", location);
        Log.d("CHEN111111", website);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        initViews();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8082/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        GetResult getResult1 = retrofit.create(GetResult.class);

         Call<List<Result>> listCall = getResult1.getSpecificResult(keyword,time,date,location,website);
                 //getResult();

        listCall.enqueue(new Callback<List<Result>>() {
            @Override
            public void onResponse(Call<List<Result>> call, Response<List<Result>> response) {
                if (!response.isSuccessful()) {
                    Log.d("Code", "code " +response.code());
                 //   textView.setText("Code " + response.code());
                    return;
                }
                Log.d("MMMMM","chen ifargannnnnnnnn" + "\n");

                List<Result> results = response.body();

                for (Result result : results) {

                    String alertID = result.getAlertid();
                    String website= result.getWebsite() ;
                    String location= result.getLocation() ;
                    String timestamp=  result.getTimestamp() ;
                    String feedback=  result.getFeedback() ;
                    String text=  result.getText() ;
                    String publisher = result.getPublisher() ;
                    String keywords = result.getKeywords() ;
                    arrayList.add(new Result(alertID,website,location,timestamp,feedback,text,publisher,keywords));
                    //Log.d("TAG", content);

                }

            }

            @Override
            public void onFailure(Call<List<Result>> call, Throwable t) {
                Log.d("72 chen", t.getMessage());

                // textView.setText(t.getMessage());
            }
        });



        databaseReference.child("History").child(userID).addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    ResultPerUserID resultPerUserID = new ResultPerUserID(userID);
                    ArrayList <Result> resultArrayList =  new ArrayList<Result>();
                    for (DataSnapshot dss : snapshot.getChildren()){
                        Result r1= dss.getValue(Result.class);
                        resultArrayList.add(r1);
                    }
                   margeArrayList= mergedResults(arrayList,resultArrayList);
                    //get from the service the new result
                    //resultArrayList.add(new Result(alertID,website,location,timestamp,feedback,text,publisher,keywords));
                    resultPerUserID.setResultArrayList(margeArrayList);
                    databaseReference.child("History").child(userID).setValue(margeArrayList).addOnCompleteListener(new OnCompleteListener<Void>() {
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
                    ArrayList <Result> array= resultPerUserID.addToArray(arrayList);
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
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Put your code here
                lottieAnimationView.pauseAnimation();
                Intent myIntent = new Intent(RequestsActivity.this, ShowOnMapActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userID", userID);
                myIntent.putExtras(bundle);
                startActivity(myIntent);
                finish();
            }
        }, 30000); // 30 seconds delay

    }

    private ArrayList<Result> mergedResults(ArrayList<Result> arrayList, ArrayList<Result> resultArrayList) {
        Set<String> alertIds = new HashSet<>();
        ArrayList<Result> mergedResult = new ArrayList<>();

        for (Result result : arrayList) {
            if (!alertIds.contains(result.getAlertid())) {
                alertIds.add(result.getAlertid());
                mergedResult.add(result);
            }
        }

        for (Result result : resultArrayList) {
            if (!alertIds.contains(result.getAlertid())) {
                alertIds.add(result.getAlertid());
                mergedResult.add(result);
            }
        }
        return mergedResult;


    }
    private void initViews() {
        lottieAnimationView = findViewById(R.id.animationView);
    }








        // lottieAnimationView.pauseAnimation();

   /*     Intent myIntent = null;
     Bundle bundle = new Bundle();
                bundle.putString("alertID",alertID);
                myIntent.putExtras(bundle);
        myIntent= new Intent(RequestsActivity.this, ShowOnMapActivity.class);// activity google map
        startActivity(myIntent);
        finish();*/
    }


