package com.example.docta.myapplication;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.docta.myapplication.Classes.Avatar;
import com.example.docta.myapplication.Classes.Database.AvatarDAO;


import java.util.ArrayList;


public class PurchaseAvatarsActivity extends AppCompatActivity {

    private Button btn_back;
    private Boolean isChecked=false;
    private SharedPreferences sharedPreferences;
    private ArrayList<Avatar> app_avatars=new ArrayList<>();
    private AvatarDAO avatarDAO;
    private ImageView avatar1;
    private ImageView avatar2;
    private ImageView avatar3;
    private ImageView avatar4;
    private ImageView avatar5;
    private ImageView avatar6;
    private ImageView myavatar;
    private TextView tvAvatar1Name;
    private TextView tvAvatar2Name;
    private TextView tvAvatar3Name;
    private TextView tvAvatar4Name;
    private TextView tvAvatar5Name;
    private TextView tvAvatar6Name;
    private TextView tvAvatar1Price;
    private TextView tvAvatar2Price;
    private TextView tvAvatar3Price;
    private TextView tvAvatar4Price;
    private TextView tvAvatar5Price;
    private TextView tvAvatar6Price;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_avatars);

        initComponents();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState==null){
            String title = getString(R.string.Titlu_Avatare);
            this.setTitle(title);

        }else{
            Bitmap btm=null;
            btm=BitmapFactory.decodeByteArray(app_avatars.get(0).getImage(),0,app_avatars.get(0).getImage().length);
            avatar1.setImageBitmap(Bitmap.createScaledBitmap(btm,50,50,false));
        }
    }

    private void initComponents(){

        avatarDAO = new AvatarDAO(getApplicationContext());
        btn_back=findViewById(R.id.purchase_btn_back);
        myavatar = findViewById(R.id.purchase_iv_myavatar);
        avatar1=findViewById(R.id.purchase_iv_avatar1);
        avatar2=findViewById(R.id.purchase_iv_avatar2);
        avatar3=findViewById(R.id.purchase_iv_avatar3);
        avatar4=findViewById(R.id.purchase_iv_avatar4);
        avatar5=findViewById(R.id.purchase_iv_avatar5);
        avatar6=findViewById(R.id.purchase_iv_avatar6);
        tvAvatar1Name=findViewById(R.id.purchase_tv_avatar1_name);
        tvAvatar2Name=findViewById(R.id.purchase_tv_avatar2_name);
        tvAvatar3Name=findViewById(R.id.purchase_tv_avatar3_name);
        tvAvatar4Name=findViewById(R.id.purchase_tv_avatar4_name);
        tvAvatar5Name=findViewById(R.id.purchase_tv_avatar5_name);
        tvAvatar6Name=findViewById(R.id.purchase_tv_avatar6_name);
        tvAvatar1Price=findViewById(R.id.purchase_tv_cost1);
        tvAvatar2Price=findViewById(R.id.purchase_tv_cost2);
        tvAvatar3Price=findViewById(R.id.purchase_tv_cost3);
        tvAvatar4Price=findViewById(R.id.purchase_tv_cost4);
        tvAvatar5Price=findViewById(R.id.purchase_tv_cost5);
        tvAvatar6Price=findViewById(R.id.purchase_tv_cost6);


        avatarDAO.open();
        app_avatars=avatarDAO.findAllAvatarsFromApp();
        avatarDAO.close();
        Bitmap btm=null;
        if(app_avatars.size()!=0){

            btm=BitmapFactory.decodeByteArray(app_avatars.get(0).getImage(),0,app_avatars.get(0).getImage().length);
            avatar1.setImageBitmap(Bitmap.createBitmap(btm));
            tvAvatar1Name.setText(app_avatars.get(0).getName());
            tvAvatar1Price.setText(app_avatars.get(0).getPrice().toString()+ getString(R.string.purchase_tv_price_points));
            btm=BitmapFactory.decodeByteArray(app_avatars.get(1).getImage(),0,app_avatars.get(1).getImage().length);
            avatar2.setImageBitmap(Bitmap.createBitmap(btm));
            tvAvatar2Name.setText(app_avatars.get(1).getName());
            tvAvatar2Price.setText(app_avatars.get(1).getPrice().toString()+ getString(R.string.purchase_tv_price_points));
            btm=BitmapFactory.decodeByteArray(app_avatars.get(2).getImage(),0,app_avatars.get(2).getImage().length);
            avatar3.setImageBitmap(Bitmap.createBitmap(btm));
            tvAvatar3Name.setText(app_avatars.get(2).getName());
            tvAvatar3Price.setText(app_avatars.get(2).getPrice().toString()+ getString(R.string.purchase_tv_price_points));
            btm=BitmapFactory.decodeByteArray(app_avatars.get(3).getImage(),0,app_avatars.get(3).getImage().length);
            avatar4.setImageBitmap(Bitmap.createBitmap(btm));
            tvAvatar4Name.setText(app_avatars.get(3).getName());
            tvAvatar4Price.setText(app_avatars.get(3).getPrice().toString()+ getString(R.string.purchase_tv_price_points));
            btm=BitmapFactory.decodeByteArray(app_avatars.get(4).getImage(),0,app_avatars.get(4).getImage().length);
            avatar5.setImageBitmap(Bitmap.createBitmap(btm));
            tvAvatar5Name.setText(app_avatars.get(4).getName());
            tvAvatar5Price.setText(app_avatars.get(4).getPrice().toString()+ getString(R.string.purchase_tv_price_points));
            btm=BitmapFactory.decodeByteArray(app_avatars.get(5).getImage(),0,app_avatars.get(5).getImage().length);
            avatar6.setImageBitmap(Bitmap.createBitmap(btm));
            tvAvatar6Name.setText(app_avatars.get(5).getName());
            tvAvatar6Price.setText(app_avatars.get(5).getPrice().toString()+ getString(R.string.purchase_tv_price_points));


        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }

}
