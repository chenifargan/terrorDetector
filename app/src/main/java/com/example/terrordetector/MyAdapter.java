package com.example.terrordetector;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>

{
    private Context context;
    private ArrayList<Result> result;
    private OnRecordEventListener mClickListener;


    public MyAdapter( Context context,ArrayList<Result> result,OnRecordEventListener listener) {
        this.context = context;
        this.result = result;
        this.mClickListener =listener;
    }



//    public MyAdapter setResultItemClickListener(resultItemClickListener listener) {
//        this.resultItemClickListener = listener;
//        return this;
//    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_result, parent, false);
        MyViewHolder myResultViewHolder = new MyViewHolder(view,mClickListener);
        return myResultViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.tv_website.setText(result.get(position).getWebsite());
        holder.tv_location.setText(result.get(position).getLocation());
        holder.tv_text.setText(result.get(position).getText());
        holder.tv_timestamp.setText(result.get(position).getTimestamp());
        holder.tv_keywords.setText((CharSequence) result.get(position).getKeywords());
        holder.tv_publisher.setText(result.get(position).getPublisher());
        holder.ed_feedback.setText(result.get(position).getFeedback());

        holder.ed_feedback.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            mClickListener.onRatingBarChange(result.get(position),holder.ed_feedback.getText().toString(),position);
            }
        });


        String locationName = result.get(position).getLocation();
        Geocoder geocoder = new Geocoder(context);
        double latitude=0;
        double longitude=0;
        try {
            List<Address> addresses = geocoder.getFromLocationName(locationName, 1);

            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                latitude = address.getLatitude();
                 longitude = address.getLongitude();

                Log.d("Latitude ","Latitude: " + latitude);
                //System.out.println("Longitude: " + longitude);
            } else {
                Log.d("TAG", "No coordinates found for the given location name.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //double Latitude= 31.771959;// Double.parseDouble(str.split(",")[0]);
        //double Longitude=35.217018;//Double.parseDouble(str.split(",")[1]);

        int imageWidth= holder.result_IMG_image.getWidth()+500;
        int imageHeight = holder.result_IMG_image.getHeight()+500;
      //  https://maps.googleapis.com/maps/api/staticmap?size=500x500&path=color:0x0000ff|weight:5|31.0461,34.8516&zoom=15&key=AIzaSyDTMuClb0tvUOdv6BpjZmQ0nSCianAR7gw
        String url = "https://maps.googleapis.com/maps/api/staticmap?"+
                "size="+imageWidth+"x"+ imageHeight +
                "&center=" + latitude +","+longitude +
                "&maptype=hybrid"+
                "&markers= size:mid%7Ccolor:red"+
               // "&markers=color:blue%7Clabel:S%7C11211%7C11206%7C11222"+
                "&zoom=15"+
                "&key=AIzaSyDTMuClb0tvUOdv6BpjZmQ0nSCianAR7gw";
        Glide.with(context)
                .load(url)
                .into(holder.result_IMG_image);
      //  downloadMapImage(holder,result.get(position).getLocation());
//        holder._RTNG_stars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                Log.d("TAG", "onRatingChanged: " + rating);
//                rating /= 20;
//                result.get(position).setFeedback(String.valueOf(rating));
//                holder._RTNG_stars.setRating(rating);
//            }
//        });

    }

    private void downloadMapImage(MyViewHolder holder, String location) {
        int zoom =18;

    }

    @Override
    public int getItemCount() {
        return result== null ?0 : result.size();
    }
    private Result getItem(int position) {
        return result.get(position);
    }

    public interface OnRecordEventListener  {
        void onRatingBarChange(Result item,String value,int position);
    }



    class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatImageView result_IMG_image;
        public TextView tv_website, tv_location, tv_publisher, tv_text,tv_timestamp,tv_keywords;
        public RatingBar _RTNG_stars;
        public EditText ed_feedback;
        public MyViewHolder(View itemView,OnRecordEventListener listener) {
            super(itemView);
            this.result_IMG_image= itemView.findViewById(R.id.result_IMG_image);
            this.tv_website = itemView.findViewById(R.id.tv_website);
            this.tv_location = itemView.findViewById(R.id.tv_location);
            this.tv_publisher= itemView.findViewById(R.id.tv_publisher);
            this.tv_text = itemView.findViewById(R.id.tv_text);
            this.tv_timestamp= itemView.findViewById(R.id.tv_timestamp);
            this.tv_keywords = itemView.findViewById(R.id.tv_keywords);
            this.ed_feedback = itemView.findViewById(R.id.ed_feedback);
          //  this._RTNG_stars = itemView.findViewById(R.id.movie_RTNG_stars);

//            _RTNG_stars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//                @Override
//                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                    Log.d("cheni", "onRatingChanged: ");
//                    listener.onRatingBarChange(result.get(getLayoutPosition()),rating);
//                }
//            });
//        }


    }
}}
