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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
    private ImageView ivAvatar2;
    private ImageView ivAvatar3;
    private TextView tvAvatar1Name;
    private TextView tvAvatar2Name;
    private TextView tvAvatar3Name;
    private int ivSelected;
    Intent intent;

    private ArrayList<Avatar> avatarsFromPhone=new ArrayList<>();



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
        ivAvatar2=findViewById(R.id.myavatars_iv_avatar2);
        ivAvatar3=findViewById(R.id.myavatars_iv_avatar3);
        tvAvatar1Name=findViewById(R.id.myavatars_tv_avatar1_name);
        tvAvatar2Name=findViewById(R.id.myavatars_tv_avatar2_name);
        tvAvatar3Name=findViewById(R.id.myavatars_tv_avatar3_name);
        avatarDAO.open();
        avatarsFromPhone=avatarDAO.findAllAvatarsFromPhone();
        avatarDAO.close();
        initImageViews(avatarsFromPhone);

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
        tvAvatar1Name.setOnClickListener(changeName1());
        tvAvatar2Name.setOnClickListener(changeName2());
        tvAvatar3Name.setOnClickListener(changeName3());
        ivAvatar1.setOnClickListener(deleteAvatar1());

    }
    private View.OnClickListener deleteAvatar1(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatarDAO.open();
                avatarDAO.deleteAvatarFromPhone(avatarsFromPhone.get(0));
                avatarDAO.close();
            }
        };
    }
    private void initImageViews(ArrayList<Avatar> avatars){
        /*avatarDAO.open();
        avatarsFromPhone=avatarDAO.findAllAvatarsFromPhone();
        avatarDAO.close();*/
        if(avatars.size()==1){
            Bitmap btm=BitmapFactory.decodeByteArray(avatars.get(0).getImage(),0,avatars.get(0).getImage().length);
            ivAvatar1.setImageBitmap(Bitmap.createBitmap(btm));
            tvAvatar1Name.setText(avatars.get(0).getName());}
        else if   (avatars.size()==2){
            Bitmap btm=BitmapFactory.decodeByteArray(avatars.get(0).getImage(),0,avatars.get(0).getImage().length);
            ivAvatar1.setImageBitmap(Bitmap.createBitmap(btm));
            tvAvatar1Name.setText(avatars.get(0).getName());
            Bitmap btm2=BitmapFactory.decodeByteArray(avatars.get(1).getImage(),0,avatars.get(1).getImage().length);
            ivAvatar2.setImageBitmap(Bitmap.createBitmap(btm2));
            tvAvatar2Name.setText(avatars.get(1).getName());}
         else if (avatars.size()>=3) {
            Bitmap btm=BitmapFactory.decodeByteArray(avatars.get(0).getImage(),0,avatars.get(0).getImage().length);
            ivAvatar1.setImageBitmap(Bitmap.createBitmap(btm));
            tvAvatar1Name.setText(avatars.get(0).getName());
            Bitmap btm2=BitmapFactory.decodeByteArray(avatars.get(1).getImage(),0,avatars.get(1).getImage().length);
            ivAvatar2.setImageBitmap(Bitmap.createBitmap(btm2));
            tvAvatar2Name.setText(avatars.get(1).getName());
            Bitmap btm3 = BitmapFactory.decodeByteArray(avatars.get(2).getImage(), 0, avatars.get(2).getImage().length);
            ivAvatar3.setImageBitmap(Bitmap.createBitmap(btm3));
            tvAvatar3Name.setText(avatars.get(2).getName());
        }

    }
    private View.OnClickListener changeName1(){
        return  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivSelected=0;
                Intent intent= new Intent(getApplicationContext(), UpdateAvatarNameActivity.class);
                startActivityForResult(intent, Constants.UPDATE_AVATAR_REQUEST_CODE);
            }
        };
    }
    private View.OnClickListener changeName2(){
        return  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivSelected=1;
                Intent intent= new Intent(getApplicationContext(), UpdateAvatarNameActivity.class);
                startActivityForResult(intent, Constants.UPDATE_AVATAR_REQUEST_CODE);
            }
        };
    }
    private View.OnClickListener changeName3(){
        return  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivSelected=2;
                Intent intent= new Intent(getApplicationContext(), UpdateAvatarNameActivity.class);
                startActivityForResult(intent, Constants.UPDATE_AVATAR_REQUEST_CODE);
            }
        };
    }
    private View.OnClickListener uploadImage(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, Constants.UPLOAD_IMAGE_REQUEST_CODE);

            }
        };
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {       Bitmap mBitmap=null;
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode==Constants.UPLOAD_IMAGE_REQUEST_CODE)
        {
            Uri chosenImageUri = data.getData();
            try {
                mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), chosenImageUri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mBitmap .compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                Avatar avatar=new Avatar(byteArray);
                avatarDAO.open();
                long id=  avatarDAO.insertAvatarInDatabase(avatar);
                avatarDAO.close();


            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Nu s-a putut incarca imaginea",Toast.LENGTH_LONG).show();
            }
        }
        else if(resultCode==RESULT_OK && requestCode==Constants.UPDATE_AVATAR_REQUEST_CODE && data!=null){
            String newName= data.getStringExtra(Constants.SET_NAME_KEY);
            tvAvatar1Name.setText(newName);
            avatarDAO.open();
            avatarsFromPhone=avatarDAO.findAllAvatarsFromPhone();
            avatarDAO.updatePhoneAvatar(newName,avatarsFromPhone.get(ivSelected).getId() );
            avatarDAO.close();
        }


    }
}
