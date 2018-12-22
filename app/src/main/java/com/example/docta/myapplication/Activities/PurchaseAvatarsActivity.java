package com.example.docta.myapplication.Activities;

import android.annotation.SuppressLint;
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
import com.example.docta.myapplication.Classes.Database.StudentDAO;
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
    private StudentDAO studentDao;
    private ImageView myavatar;
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
            imageViewsAvatarList.get(0).setImageBitmap(Bitmap.createScaledBitmap(btm,50,50,false));
        }
    }

    @SuppressLint("FindViewByIdCast")
    private void initComponents(){
        btn_back=findViewById(R.id.purchase_btn_back);
        myavatar = findViewById(R.id.purchase_iv_myavatar);
        avatarDAO = new AvatarDAO(getApplicationContext());
        associativeDAO= new AssociativeDAO(getApplicationContext());
        sharedPreferencesUser= getSharedPreferences(Constants.USERNAME_PREF,MODE_PRIVATE);
        user=sharedPreferencesUser.getString(Constants.USERNAME_KEY,getString(R.string.default_user_pref));
        userAvatars = (ArrayList<Avatar>) getIntent().getSerializableExtra(Constants.USER_AVATAR_KEY);
        textViewsBuy.add(findViewById(R.id.purchase_tv_buyed1));
        textViewsBuy.add(findViewById(R.id.purchase_tv_buyed2));
        textViewsBuy.add(findViewById(R.id.purchase_tv_buyed3));
        textViewsBuy.add(findViewById(R.id.purchase_tv_buyed4));
        textViewsBuy.add(findViewById(R.id.purchase_tv_buyed5));
        textViewsBuy.add(findViewById(R.id.purchase_tv_buyed6));

        imageViewsAvatarList.add(findViewById(R.id.purchase_iv_avatar1));
        imageViewsAvatarList.add(findViewById(R.id.purchase_iv_avatar2));
        imageViewsAvatarList.add(findViewById(R.id.purchase_iv_avatar3));
        imageViewsAvatarList.add(findViewById(R.id.purchase_iv_avatar4));
        imageViewsAvatarList.add(findViewById(R.id.purchase_iv_avatar5));
        imageViewsAvatarList.add(findViewById(R.id.purchase_iv_avatar6));

        textViewsNameList.add(findViewById(R.id.purchase_tv_avatar1_name));
        textViewsNameList.add(findViewById(R.id.purchase_tv_avatar2_name));
        textViewsNameList.add(findViewById(R.id.purchase_tv_avatar3_name));
        textViewsNameList.add(findViewById(R.id.purchase_tv_avatar4_name));
        textViewsNameList.add(findViewById(R.id.purchase_tv_avatar5_name));
        textViewsNameList.add(findViewById(R.id.purchase_tv_avatar6_name));

        textViewsPriceList.add(findViewById(R.id.purchase_tv_cost1));
        textViewsPriceList.add(findViewById(R.id.purchase_tv_cost2));
        textViewsPriceList.add(findViewById(R.id.purchase_tv_cost3));
        textViewsPriceList.add(findViewById(R.id.purchase_tv_cost4));
        textViewsPriceList.add(findViewById(R.id.purchase_tv_cost5));
        textViewsPriceList.add(findViewById(R.id.purchase_tv_cost6));

        for (int i=0; i<imageViewsAvatarList.size();i++){
            imageViewsAvatarList.get(i).setOnLongClickListener(buyAvatar(i));
        }

        avatarDAO.open();
        app_avatars=avatarDAO.findAllAvatarsFromApp();
        avatarDAO.close();


        studentDao=new StudentDAO(this);

        studentDao.open();
        Bitmap btm=null;
        btm=BitmapFactory.decodeByteArray(studentDao.findMyAvatar(user),0,studentDao.findMyAvatar(user).length);
        myavatar.setImageBitmap(Bitmap.createBitmap(btm));
        studentDao.close();

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
    @SuppressLint("SetTextI18n")
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
                        .setPositiveButton(getString(R.string.positive_button), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(userAvatars.size()<=2) {
                                    Double score;
                                    studentDao.open();
                                    score = studentDao.findScoreByUser(user);
                                    studentDao.close();
                                    if (score >= app_avatars.get(position).getPrice()) {
                                        associativeDAO.open();
                                        long result = associativeDAO.insertAssociativeAvatar(user, app_avatars.get(position).getId());
                                        associativeDAO.close();
                                        if (result != -1) {
                                            Toast.makeText(getApplicationContext(), getString(R.string.purchase_toast_is_buy), Toast.LENGTH_LONG).show();
                                            studentDao.open();
                                                studentDao.updateScore(-app_avatars.get(position).getPrice(),user);
                                            studentDao.close();
                                            Intent intent = new Intent(getApplicationContext(), MyAvatarsActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                else{
                                        Toast.makeText(getApplicationContext(), getString(R.string.not_enough_points), Toast.LENGTH_LONG).show();

                                    }
                                }
                                     else {
                                        Toast.makeText(getApplicationContext(), getString(R.string.purchase_toast_delete_one_avatar), Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), MyAvatarsActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }

                            }
                        })
                        .setNegativeButton(getString(R.string.negative_button), new DialogInterface.OnClickListener() {
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
