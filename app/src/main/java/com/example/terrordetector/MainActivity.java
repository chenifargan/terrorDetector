package com.example.terrordetector;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.Address;


public class MainActivity extends AppCompatActivity {

   private EditText infoInsertWord,infoLocation,infoTime, infoWeb,infoDate;
    private Button btnShowHistory,btnMyLocation,btn;
    int hour, minute,day,month,year;
    private Spinner spinnerWebsite;
    private LocationRequest locationRequest;
    private AutoCompleteTextView autoCompleteTextViewInsertWord;
    private ArrayAdapter <String> arrayAdapterInsertWord;
    private ArrayAdapter <CharSequence> arrayAdapterSpinnerWeb;
    private String [] itemsInsertWord ;//= {"boom","Instagram","Twitter","Tik Tok"};//need to complete word
    private String userID;
   // String apiKey = getString(R.string.api_key);
   String latitude="" , coordinates,longitude="";








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userID = "Y2hlbg==";//getIntent().getExtras().getString("userID");
        Log.d("TAG", "alertId "+ userID);
        initViews();



        infoLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(MainActivity.this);
                startActivityForResult(intent,100);
            }
        });



        btnMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){

                        if(isGPSEnabled()){
                            LocationServices.getFusedLocationProviderClient(MainActivity.this)
                                    .requestLocationUpdates(locationRequest, new LocationCallback() {
                                        @Override
                                        public void onLocationResult(@NonNull LocationResult locationResult) {
                                            super.onLocationResult(locationResult);
                                            LocationServices.getFusedLocationProviderClient(MainActivity.this)
                                                    .removeLocationUpdates(this);
                                            Log.d("TAG", "onLocationResult: ch");
                                            if(locationResult!=null && locationResult.getLocations().size()>0){
                                                int index = locationResult.getLocations().size()-1;
                                                double latitude = locationResult.getLocations().get(index).getLatitude();
                                                double longitude = locationResult.getLocations().get(index).getLongitude();
                                                infoLocation.setText("Lat: "+latitude +"\n"+ "longi: "+longitude);
                                            }
                                        }
                                    }, Looper.getMainLooper());


                        }
                        else{
                            turnOnGPS();

                        }

                    }
                    else{
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    }
                }
                else{
                    Toast.makeText( MainActivity.this,"notgood",Toast.LENGTH_SHORT).show();
                }
            }
        });
        autoCompleteTextViewInsertWord.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
            @Override
            public void onDismiss() {
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(),0);

            }
        });
        btnShowHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, HistoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userID", userID);
                myIntent.putExtras(bundle);
               // myIntent= new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(myIntent);
            }
        });




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //need to do



                Intent myIntent= new Intent(MainActivity.this, RequestsActivity.class);// activity google map

                Bundle bundle = new Bundle();
               bundle.putString("userID",userID);
               bundle.putString("keyword",autoCompleteTextViewInsertWord.getText().toString().trim());
                bundle.putString("time",infoTime.getText().toString().trim());
                bundle.putString("date",infoDate.getText().toString().trim());
                bundle.putString("location",infoLocation.getText().toString().trim());
                bundle.putString("website",spinnerWebsite.getSelectedItem().toString());

                myIntent.putExtras(bundle);
                startActivity(myIntent);
            }
        });

    }

    private void turnOnGPS() {


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(MainActivity.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(MainActivity.this, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

    }

    private boolean isGPSEnabled(){
        LocationManager locationManager =null;
        boolean isEnabled = false;
        if(locationManager== null){
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }
        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return  isEnabled;
    }


    public void popDataPicker(View view){
        final Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);


        int style = AlertDialog.THEME_DEVICE_DEFAULT_DARK;

        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,style, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year1, int month1, int dayOfMonth) {

                day = dayOfMonth;
                month = month1+1;
                year = year1;
                infoDate.setText(String.format(Locale.getDefault(), "%02d/%02d/%04d", day, month, year));

            }

        }
        ,day,month,year);

        datePickerDialog.setTitle("Select date");
        datePickerDialog.show();

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
    readfromfile();
        infoLocation = findViewById(R.id.et_location);
        infoTime= findViewById(R.id.et_time);
        infoTime.setShowSoftInputOnFocus(false);
    //    infoWeb= findViewById(R.id.et_web);
        infoDate = findViewById(R.id.et_date);
        infoDate.setShowSoftInputOnFocus(false);
        btnMyLocation = findViewById(R.id.btn_getmylocation);
        btn = findViewById(R.id.btn_find);
        spinnerWebsite = findViewById(R.id.spinnerWeb);
        autoCompleteTextViewInsertWord = findViewById(R.id.autoCompleteKeyword);
        btnShowHistory = findViewById(R.id.btn_ShowHistory);
        arrayAdapterInsertWord = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_item,itemsInsertWord);
        autoCompleteTextViewInsertWord.setAdapter(arrayAdapterInsertWord);
        arrayAdapterSpinnerWeb = ArrayAdapter.createFromResource(MainActivity.this,R.array.website, android.R.layout.simple_spinner_item);
        arrayAdapterSpinnerWeb.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerWebsite.setAdapter(arrayAdapterSpinnerWeb);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        String apiKey = getString(R.string.api_key);


        Places.initialize(getApplicationContext(),apiKey);
        infoLocation.setFocusable(false);
    }

    private void readfromfile() {
        String file_name= "words_lexicon.txt";
        ArrayList<String> stringList = new ArrayList<String>();
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.words_lexicon);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringList.add(line);
            }
            reader.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        itemsInsertWord = stringList.toArray(new String[stringList.size()]);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100&& resultCode==RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            infoLocation.setText(place.getAddress());

           coordinates =String.valueOf(place.getLatLng());
            String[] parts = coordinates.replaceAll("[()]", "").split(",");
             latitude = parts[0].trim();
             longitude = parts[1].trim();

        }
            else if(requestCode == AutocompleteActivity.RESULT_ERROR){
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(),status.getStatusMessage(),Toast.LENGTH_SHORT).show();
            }

        }
}