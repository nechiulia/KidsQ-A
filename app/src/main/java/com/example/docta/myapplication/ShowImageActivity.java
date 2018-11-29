package com.example.docta.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ShowImageActivity extends AppCompatActivity {
 Intent intent;
    String imagine;
    ImageView ivImagine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        imagine= getIntent().getStringExtra("Imagine");

       ivImagine=findViewById(R.id.imageView);
       ivImagine.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });
        loadImageFromJson(imagine);
    }
    private void loadImageFromJson(String urlJson){
        Picasso.with(getApplicationContext()).load(urlJson).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
                .into(ivImagine, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }
}
