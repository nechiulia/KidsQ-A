package com.example.docta.myapplication.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.docta.myapplication.Classes.Database.AssociativeDAO;
import com.example.docta.myapplication.Classes.util.Avatar;
import com.example.docta.myapplication.Classes.Database.AvatarDAO;
import com.example.docta.myapplication.Classes.util.Constants;
import com.example.docta.myapplication.R;


import java.util.ArrayList;


public class PurchaseAvatarsActivity extends AppCompatActivity {

    private Button btn_back;
    private Boolean isChecked=false;
    private SharedPreferences sharedPreferences;
    private ArrayList<Avatar> app_avatars=new ArrayList<>();
    private AvatarDAO avatarDAO;
    private AssociativeDAO associativeDAO;
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
    private TextView tvAvatar1Buy;
    private TextView tvAvatar2Buy;
    private TextView tvAvatar3Buy;
    private TextView tvAvatar4Buy;
    private TextView tvAvatar5Buy;
    private TextView tvAvatar6Buy;
    private SharedPreferences sharedPreferencesUser;
    private String user;
   private ArrayList<TextView> textViewsNameList=new ArrayList<>();
   private ArrayList<TextView> textViewsPriceList= new ArrayList<>();
   private ArrayList<ImageView> imageViewsAvatarList= new ArrayList<>();
   private ArrayList<Avatar> userAvatars=new ArrayList<>();
   private ArrayList<TextView> textViewsBuy= new ArrayList<>();
    private Intent intent;

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
        associativeDAO= new AssociativeDAO(getApplicationContext());
        sharedPreferencesUser= getSharedPreferences(Constants.USERNAME_PREF,MODE_PRIVATE);
        user= sharedPreferencesUser.getString(Constants.USERNAME_KEY, "user");
        userAvatars=(ArrayList<Avatar>) getIntent().getSerializableExtra(Constants.USER_AVATAR_KEY);
        tvAvatar1Buy=findViewById(R.id.purchase_tv_buyed1);
        textViewsBuy.add(tvAvatar1Buy);
        tvAvatar2Buy=findViewById(R.id.purchase_tv_buyed2);
        textViewsBuy.add(tvAvatar2Buy);
        tvAvatar3Buy=findViewById(R.id.purchase_tv_buyed3);
        textViewsBuy.add(tvAvatar3Buy);
        tvAvatar4Buy=findViewById(R.id.purchase_tv_buyed4);
        textViewsBuy.add(tvAvatar4Buy);
        tvAvatar5Buy=findViewById(R.id.purchase_tv_buyed5);
        textViewsBuy.add(tvAvatar5Buy);
        tvAvatar6Buy=findViewById(R.id.purchase_tv_buyed6);
        textViewsBuy.add(tvAvatar6Buy);
       /* textViewsBuy.add(findViewById(R.id.purchase_tv_buyed1));
        textViewsBuy.add(findViewById(R.id.purchase_tv_buyed2));
        textViewsBuy.add(findViewById(R.id.purchase_tv_buyed3));
        textViewsBuy.add(findViewById(R.id.purchase_tv_buyed4));
        textViewsBuy.add(findViewById(R.id.purchase_tv_buyed5));
        textViewsBuy.add(findViewById(R.id.purchase_tv_buyed6));*/

        btn_back=findViewById(R.id.purchase_btn_back);
        myavatar = findViewById(R.id.purchase_iv_myavatar);
        avatar1=findViewById(R.id.purchase_iv_avatar1);
        avatar2=findViewById(R.id.purchase_iv_avatar2);
        avatar3=findViewById(R.id.purchase_iv_avatar3);
        avatar4=findViewById(R.id.purchase_iv_avatar4);
        avatar5=findViewById(R.id.purchase_iv_avatar5);
        avatar6=findViewById(R.id.purchase_iv_avatar6);
        imageViewsAvatarList.add(avatar1);
        imageViewsAvatarList.add(avatar2);
        imageViewsAvatarList.add(avatar3);
        imageViewsAvatarList.add(avatar4);
        imageViewsAvatarList.add(avatar5);
        imageViewsAvatarList.add(avatar6);

        tvAvatar1Name=findViewById(R.id.purchase_tv_avatar1_name);
        tvAvatar2Name=findViewById(R.id.purchase_tv_avatar2_name);
        tvAvatar3Name=findViewById(R.id.purchase_tv_avatar3_name);
        tvAvatar4Name=findViewById(R.id.purchase_tv_avatar4_name);
        tvAvatar5Name=findViewById(R.id.purchase_tv_avatar5_name);
        tvAvatar6Name=findViewById(R.id.purchase_tv_avatar6_name);
        textViewsNameList.add(tvAvatar1Name);
        textViewsNameList.add(tvAvatar2Name);
        textViewsNameList.add(tvAvatar3Name);
        textViewsNameList.add(tvAvatar4Name);
        textViewsNameList.add(tvAvatar5Name);
        textViewsNameList.add(tvAvatar6Name);

        tvAvatar1Price=findViewById(R.id.purchase_tv_cost1);
        tvAvatar2Price=findViewById(R.id.purchase_tv_cost2);
        tvAvatar3Price=findViewById(R.id.purchase_tv_cost3);
        tvAvatar4Price=findViewById(R.id.purchase_tv_cost4);
        tvAvatar5Price=findViewById(R.id.purchase_tv_cost5);
        tvAvatar6Price=findViewById(R.id.purchase_tv_cost6);
        textViewsPriceList.add(tvAvatar1Price);
        textViewsPriceList.add(tvAvatar2Price);
        textViewsPriceList.add(tvAvatar3Price);
        textViewsPriceList.add(tvAvatar4Price);
        textViewsPriceList.add(tvAvatar5Price);
        textViewsPriceList.add(tvAvatar6Price);

        avatar1.setOnLongClickListener(buyAvatar(0));
        avatar2.setOnLongClickListener(buyAvatar(1));
        avatar3.setOnLongClickListener(buyAvatar(2));
        avatar4.setOnLongClickListener(buyAvatar(3));
        avatar5.setOnLongClickListener(buyAvatar(4));
        avatar6.setOnLongClickListener(buyAvatar(5));

        avatarDAO.open();
        app_avatars=avatarDAO.findAllAvatarsFromApp();
        avatarDAO.close();

        initControllers();
        boolean exists=false;
        for(int i=0;i< textViewsBuy.size();i++){
            exists=false;
            if(userAvatars.size()!=1){
            for(int j=0;j<userAvatars.size();j++) {
                if (userAvatars.get(j).getId().equals(app_avatars.get(i).getId()) ) {
                    exists = true;
                    imageViewsAvatarList.get(i).setOnLongClickListener(null);
                    imageViewsAvatarList.get(i).setAlpha(80);
                }
            }}
            else {
                if (userAvatars.get(0).getId().equals(app_avatars.get(i).getId()) ) {
                    exists = true;
                    imageViewsAvatarList.get(i).setOnLongClickListener(null);
                    imageViewsAvatarList.get(i).setAlpha(80);
                }
            }
            if(!exists) {
                textViewsBuy.get(i).setVisibility(View.INVISIBLE);
            }

        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), MyAvatarsActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }
    private void initControllers(){
        Bitmap btm=null;
        if(app_avatars.size()!=0){
            for(int i=0;i<app_avatars.size();i++){
                btm=BitmapFactory.decodeByteArray(app_avatars.get(i).getImage(),0,app_avatars.get(i).getImage().length);
                imageViewsAvatarList.get(i).setImageBitmap(Bitmap.createBitmap(btm));
                textViewsNameList.get(i).setText(app_avatars.get(i).getName());
                textViewsPriceList.get(i).setText(app_avatars.get(i).getPrice().toString()+ getString(R.string.purchase_tv_price_points));
            }
        }
    }
    private View.OnLongClickListener buyAvatar(int position){
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder= new AlertDialog.Builder(PurchaseAvatarsActivity.this);
                builder.setTitle(getString(R.string.purchase_toast_buy_avatar))
                        .setMessage(getString(R.string.purchase_toast_sure_buy))
                        .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(userAvatars.size()<=2) {
                                    associativeDAO.open();
                                    long result= associativeDAO.insertAssociativeAvatar(user, app_avatars.get(position).getId());
                                    associativeDAO.close();
                                    if(result!=-1) {
                                        Toast.makeText(getApplicationContext(),"Avatar cumparat!",Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),getString(R.string.purchase_toast_delete_one_avatar),Toast.LENGTH_LONG).show();
                                    finish();
                                }

                            }
                        })
                        .setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(PurchaseAvatarsActivity.this,getString(R.string.purchase_not_buy), Toast.LENGTH_LONG).show();
                            }
                        });
                builder.create().show();
                return true;
            }
        };
    }

}
