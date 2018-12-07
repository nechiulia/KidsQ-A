package com.example.docta.myapplication;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.docta.myapplication.Classes.Avatar;
import com.example.docta.myapplication.Classes.AvatarParser;
import com.example.docta.myapplication.Classes.Database.AvatarDAO;
import com.example.docta.myapplication.Classes.Database.TeacherDAO;
import com.example.docta.myapplication.Classes.Network.HttpManager;
import com.example.docta.myapplication.util.Constants;

import org.json.JSONException;

import java.util.ArrayList;

public class PurchaseAvatarsActivity extends AppCompatActivity {

    private Button btn_back;
    private Boolean isChecked;
    private SharedPreferences sharedPreferences;
    private ArrayList<Avatar> app_avatars=new ArrayList<>();
    private AvatarDAO avatarDAO;
    private ImageView avatar1;
    private ImageView avatar2;
    private ImageView avatar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_avatars);
        if(savedInstanceState==null){
            String title = getString(R.string.Titlu_Avatare);
            this.setTitle(title);
        }
        sharedPreferences = getSharedPreferences(Constants.AVATAR_UPLOAD_CHECK_PREF,MODE_PRIVATE);
        isChecked = sharedPreferences.getBoolean(getString(R.string.LOGIN_SHARED_JSONKEY),false);
        if(!isChecked){
            @SuppressLint("StaticFieldLeak") HttpManager manager = new HttpManager() {
                @Override
                protected void onPostExecute(String s) {
                    try {
                        app_avatars = AvatarParser.fromJson(s);
                        avatarDAO.open();
                        avatarDAO.insertAvatarsInDatabase(app_avatars);
                        avatarDAO.close();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            manager.execute(Constants.URL_JSON_AVATARS);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Constants.AVATAR_BOOL_CHECK_KEY,true);
            editor.commit();
        }
        else {
            app_avatars=avatarDAO.findAllAvatarsFromApp();
        }
        initComponents();
    }

    private void initComponents(){
        avatarDAO = new AvatarDAO(getApplicationContext());
        btn_back=findViewById(R.id.purchase_btn_back);
        avatar1=findViewById(R.id.myavatars_iv_avatar1);
        avatar2=findViewById(R.id.myavatars_iv_avatar2);
        avatar3=findViewById(R.id.myavatars_iv_avatar3);
        if(app_avatars.size()!=0){
            avatar1.setImageBitmap(BitmapFactory.decodeByteArray( app_avatars.get(0).getImage(),
                    0,app_avatars.get(0).getImage().length));
            avatar2.setImageBitmap(BitmapFactory.decodeByteArray( app_avatars.get(1).getImage(),
                    0,app_avatars.get(1).getImage().length));
            avatar3.setImageBitmap(BitmapFactory.decodeByteArray( app_avatars.get(2).getImage(),
                    0,app_avatars.get(2).getImage().length));
        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

}
