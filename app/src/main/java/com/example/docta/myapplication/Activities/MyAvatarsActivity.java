package com.example.docta.myapplication.Activities;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.docta.myapplication.Classes.util.Avatar;
import com.example.docta.myapplication.Classes.util.AvatarParser;
import com.example.docta.myapplication.Classes.Database.AvatarDAO;
import com.example.docta.myapplication.Classes.Network.HttpManager;
import com.example.docta.myapplication.R;
import com.example.docta.myapplication.Classes.util.Constants;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.docta.myapplication.Classes.util.Constants.URL_JSON_AVATARS;

public class MyAvatarsActivity extends AppCompatActivity {

    private Button btn_buy;
    private Button btn_back;
    private Boolean isChecked=false;
    private SharedPreferences sharedPreferences;
    private ArrayList<Avatar> app_avatars=new ArrayList<>();
    private AvatarDAO avatarDAO;
    private Button btn_add_from_pc;
    private ImageView ivAvatar1;

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
        btn_add_from_pc=findViewById(R.id.myavatars_btn_add_from_pc);
        btn_add_from_pc.setOnClickListener(uploadImage());
        ivAvatar1=findViewById(R.id.myavatars_iv_avatar1);
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

    private View.OnClickListener uploadImage(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);

            }
        };
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {       Bitmap mBitmap=null;
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            Uri chosenImageUri = data.getData();
            ArrayList<Avatar> avatarsFromPhone=new ArrayList<>();
            Avatar avatarFromPhone= new Avatar();
            try {
                mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), chosenImageUri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mBitmap .compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                Avatar avatar=new Avatar("Avatar propriu", 500.0, byteArray, 0);
                avatarDAO.open();
                long id=  avatarDAO.insertAvatarInDatabase(avatar);
                avatarDAO.close();


                if (id != -1) {
                    avatarFromPhone.setId(id);
                }
                avatarDAO.open();
                avatarsFromPhone=avatarDAO.findAllAvatarsFromPhone();
                avatarDAO.close();
                Bitmap btm=BitmapFactory.decodeByteArray(avatarsFromPhone.get(0).getImage(),0,avatarsFromPhone.get(0).getImage().length);

                ivAvatar1.setImageBitmap(Bitmap.createBitmap(btm));

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Nu s-a putut incarca imaginea",Toast.LENGTH_LONG).show();
            }
        }

    }
}
