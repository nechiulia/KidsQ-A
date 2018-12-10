package com.example.docta.myapplication;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.docta.myapplication.Classes.Avatar;
import com.example.docta.myapplication.Classes.AvatarParser;
import com.example.docta.myapplication.Classes.Database.AvatarDAO;
import com.example.docta.myapplication.Classes.Database.TeacherDAO;
import com.example.docta.myapplication.Classes.Network.HttpManager;
import com.example.docta.myapplication.util.Constants;

import org.json.JSONException;

import java.util.ArrayList;

//import static com.example.docta.myapplication.util.Global.avatars;

public class PurchaseAvatarsActivity extends AppCompatActivity {

    private Button btn_back;
    private Boolean isChecked=false;
    private SharedPreferences sharedPreferences;
    private ArrayList<Avatar> app_avatars=new ArrayList<>();
    private AvatarDAO avatarDAO;
    private ImageView avatar1;
    private ImageView avatar2;
    private ImageView avatar3;
    private ImageView myavatar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_avatars);




  //      app_avatars = avatars;

        /*if(!isChecked){
            @SuppressLint("StaticFieldLeak") HttpManager manager = new HttpManager() {
                @Override
                protected void onPostExecute(String s) {
                    try {
                        app_avatars=avatars;
                        Toast.makeText(getApplicationContext(), app_avatars.get(0).toString(),Toast.LENGTH_LONG).show();
                        avatarDAO.open();
                        avatarDAO.insertAvatarsInDatabase(app_avatars);
                        avatarDAO.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            manager.execute(Constants.URL_JSON_AVATARS);*/
        //}
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
        avatarDAO.open();
        app_avatars=avatarDAO.findAllAvatarsFromApp();
        avatarDAO.close();
        Bitmap btm=null;
        if(app_avatars.size()!=0){


            btm=BitmapFactory.decodeByteArray(app_avatars.get(0).getImage(),0,app_avatars.get(0).getImage().length);
            avatar1.setImageBitmap(Bitmap.createBitmap(btm));
            btm=BitmapFactory.decodeByteArray(app_avatars.get(1).getImage(),0,app_avatars.get(1).getImage().length);
            avatar2.setImageBitmap(Bitmap.createBitmap(btm));
            btm=BitmapFactory.decodeByteArray(app_avatars.get(2).getImage(),0,app_avatars.get(2).getImage().length);
            avatar3.setImageBitmap(Bitmap.createBitmap(btm));

        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }

}
