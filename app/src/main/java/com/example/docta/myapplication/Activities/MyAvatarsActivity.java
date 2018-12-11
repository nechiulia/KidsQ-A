package com.example.docta.myapplication.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.docta.myapplication.Classes.Avatar;
import com.example.docta.myapplication.Classes.AvatarParser;
import com.example.docta.myapplication.Classes.Database.AvatarDAO;
import com.example.docta.myapplication.Classes.Network.HttpManager;
import com.example.docta.myapplication.R;
import com.example.docta.myapplication.util.Constants;

import org.json.JSONException;

import java.util.ArrayList;

import static com.example.docta.myapplication.util.Constants.URL_JSON_AVATARS;

public class MyAvatarsActivity extends AppCompatActivity {

    private Button btn_buy;
    private Button btn_back;
    private Boolean isChecked=false;
    private SharedPreferences sharedPreferences;
    private ArrayList<Avatar> app_avatars=new ArrayList<>();
    private AvatarDAO avatarDAO;

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
        sharedPreferences = getSharedPreferences(Constants.AVATAR_UPLOAD_CHECK_PREF,MODE_PRIVATE);
        isChecked = sharedPreferences.getBoolean(Constants.AVATAR_BOOL_CHECK_KEY,false);
        if(!isChecked) {
            @SuppressLint("StaticFieldLeak") HttpManager managerJson = new HttpManager() {
                @Override
                protected void onPostExecute(String s) {
                    try {
                        app_avatars=AvatarParser.fromJson(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    finally {
                        avatarDAO.open();
                        avatarDAO.insertAvatarsInDatabase(app_avatars);
                        avatarDAO.close();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(Constants.AVATAR_BOOL_CHECK_KEY,true);
                        editor.commit();
                    }
                }
            };
            managerJson.execute(URL_JSON_AVATARS);
            //managerJson.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,URL_JSON_AVATARS);


        }


    }
    private void init(){
        btn_buy =findViewById(R.id.myavatars_btn_buy);
        btn_back=findViewById(R.id.myavatars_btn_back);
        avatarDAO = new AvatarDAO(getApplicationContext());
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
