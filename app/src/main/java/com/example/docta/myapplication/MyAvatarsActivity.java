package com.example.docta.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.docta.myapplication.Classes.Avatar;
import com.example.docta.myapplication.Classes.AvatarParser;
import com.example.docta.myapplication.Classes.Database.AvatarDAO;
import com.example.docta.myapplication.Classes.Network.HttpManager;
import com.example.docta.myapplication.Classes.TeacherParser;
import com.example.docta.myapplication.util.Constants;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MyAvatarsActivity extends AppCompatActivity {

    private Button btn_buy;
    private Button btn_back;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_avatars);
        if(savedInstanceState==null){
            String title = getString(R.string.Titlu_AvatareleMele);
            this.setTitle(title);
        }

        init();



    }
    private void init(){
        btn_buy =findViewById(R.id.myavatars_btn_buy);
        btn_back=findViewById(R.id.myavatars_btn_back);

        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getApplicationContext(), PurchaseAvatarsActivity.class);
                startActivity(intent);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


}
