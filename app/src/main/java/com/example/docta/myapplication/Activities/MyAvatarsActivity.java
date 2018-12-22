package com.example.docta.myapplication.Activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
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

import com.example.docta.myapplication.Classes.Database.AssociativeDAO;
import com.example.docta.myapplication.Classes.Database.StudentDAO;
import com.example.docta.myapplication.Classes.util.Avatar;
import com.example.docta.myapplication.Classes.util.AvatarParser;
import com.example.docta.myapplication.Classes.Database.AvatarDAO;
import com.example.docta.myapplication.Classes.Network.HttpManager;
import com.example.docta.myapplication.R;
import com.example.docta.myapplication.Classes.util.Constants;

import org.json.JSONException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.crypto.Cipher;

import static com.example.docta.myapplication.Classes.util.Constants.URL_JSON_AVATARS;

public class MyAvatarsActivity extends AppCompatActivity {

    private Button btn_buy;
    private Button btn_back;
    private Boolean isChecked=false;
    private SharedPreferences sharedPreferences;
    private SharedPreferences sharedPreferencesUser;

    private ArrayList<Avatar> app_avatars=new ArrayList<>();
    private AvatarDAO avatarDAO;
    private AssociativeDAO associativeDAO;
    private Button btn_add_from_pc;
    private ImageView ivAvatar1;
    private ImageView ivAvatar2;
    private ImageView ivAvatar3;
    private TextView tvAvatar1Name;
    private TextView tvAvatar2Name;
    private TextView tvAvatar3Name;
    private TextView tvScore;
    private int ivSelected;
    private String user;
    private ArrayList<Long> avatarsIdForUser= new ArrayList<>();
    Intent intent;
    private ArrayList<Avatar> userAvatars=new ArrayList<>();
    private ArrayList<Avatar> avatarsFromPhone=new ArrayList<>();
    ArrayList<TextView> textViewsNameList=new ArrayList<>();
    ArrayList<ImageView> imageViewsAvatarList= new ArrayList<>();
    private ImageView avatarprincipal;

    private StudentDAO studentDao;



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
                        editor.apply();
                    }
                }
            };
            managerJson.execute(URL_JSON_AVATARS);
        refreshListAvatar();
        initControllers(userAvatars);

        }


    }
    private void init(){

        avatarprincipal=findViewById(R.id.myavatars_iv_myavatar);
        btn_buy =findViewById(R.id.myavatars_btn_buy);
        btn_back=findViewById(R.id.myavatars_btn_back);
        avatarDAO = new AvatarDAO(getApplicationContext());
        associativeDAO= new AssociativeDAO(getApplicationContext());
        btn_add_from_pc=findViewById(R.id.myavatars_btn_add_from_pc);
        btn_add_from_pc.setOnClickListener(uploadImage());
        ivAvatar1=findViewById(R.id.myavatars_iv_avatar1);
        ivAvatar2=findViewById(R.id.myavatars_iv_avatar2);
        ivAvatar3=findViewById(R.id.myavatars_iv_avatar3);
        imageViewsAvatarList.add(ivAvatar1);
        imageViewsAvatarList.add(ivAvatar2);
        imageViewsAvatarList.add(ivAvatar3);
        tvAvatar1Name=findViewById(R.id.myavatars_tv_avatar1_name);
        tvAvatar2Name=findViewById(R.id.myavatars_tv_avatar2_name);
        tvAvatar3Name=findViewById(R.id.myavatars_tv_avatar3_name);
        textViewsNameList.add(tvAvatar1Name);
        textViewsNameList.add(tvAvatar2Name);
        textViewsNameList.add(tvAvatar3Name);
        tvScore=findViewById(R.id.myavatars_tv_score);
        sharedPreferencesUser= getSharedPreferences(Constants.USERNAME_PREF,MODE_PRIVATE);
        user=sharedPreferencesUser.getString(Constants.USERNAME_KEY,getString(R.string.default_user_pref));
        refreshListAvatar();
        studentDao=new StudentDAO(this);
        initControllers(userAvatars);


        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getApplicationContext(), PurchaseAvatarsActivity.class);
                intent.putExtra(Constants.USER_AVATAR_KEY, userAvatars);
                startActivity(intent);
                finish();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        for(int i=0;i<textViewsNameList.size();i++){
            textViewsNameList.get(i).setOnClickListener(changeName(i));
        }

        for(int i=0;i<imageViewsAvatarList.size();i++){
            imageViewsAvatarList.get(i).setOnLongClickListener(deleteAvatar(i));
        }

        for(int i=0;i<imageViewsAvatarList.size();i++){
            imageViewsAvatarList.get(i).setOnClickListener(updateAvatar(i));
        }
    }


    private View.OnClickListener updateAvatar(int position)
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(MyAvatarsActivity.this);
                builder.setTitle(getString(R.string.myavatars_toast_schimbaretitle))
                        .setMessage(getString(R.string.myavatars_toast_modifavatar))
                        .setPositiveButton(getString(R.string.myavatars_toast_yes), new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Bitmap btm=null;
                                btm=BitmapFactory.decodeByteArray(userAvatars.get(position).getImage(),0,userAvatars.get(position).getImage().length);
                                avatarprincipal.setImageBitmap(Bitmap.createBitmap(btm));
                                  studentDao.open();
                                  studentDao.updateAvatar(userAvatars.get(position).getImage(),user);
                                  studentDao.close();
                                Toast.makeText(MyAvatarsActivity.this, getString(R.string.myavatars_toast_update_success), Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton(getString(R.string.myavatars_toast_delete_nu), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MyAvatarsActivity.this,getString(R.string.myavatars_toast_update_failed), Toast.LENGTH_LONG).show();
                            }
                        });
                builder.create().show();



            }
        };
    }



    private View.OnLongClickListener deleteAvatar(int position){
       return new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {
               AlertDialog.Builder builder= new AlertDialog.Builder(MyAvatarsActivity.this);
               builder.setTitle(getString(R.string.myavatars_toast_delete_avatar))
                       .setMessage(getString(R.string.myavatars_toast_sure_delete))
                       .setPositiveButton(getString(R.string.myavatars_toast_yes), new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               if(userAvatars.size()==1){
                                   Toast.makeText(getApplicationContext(),getString(R.string.myavatars_toast_only_one),Toast.LENGTH_LONG).show();
                               }
                               else {
                                   int result=-1;
                                   if (userAvatars.get(position).getAppAvatar() == 1) {
                                       associativeDAO.open();
                                       result = associativeDAO.deleteAvatarById(userAvatars.get(position).getId(),user);
                                       associativeDAO.close();

                                   } else if (userAvatars.get(position).getAppAvatar() == 0) {
                                       associativeDAO.open();
                                       result = associativeDAO.deleteAvatarById(userAvatars.get(position).getId(),user);
                                       associativeDAO.close();
                                       avatarDAO.open();
                                       avatarDAO.deleteAvatarFromPhone(userAvatars.get(position).getId());
                                       avatarDAO.close();
                                   }
                                   if (result >= 0) {

                                       Avatar avatarSelectat= userAvatars.get(position);
                                       Toast.makeText(MyAvatarsActivity.this, getString(R.string.myavatars_toast_delete_ok), Toast.LENGTH_LONG).show();
                                       userAvatars.remove(position);
                                       studentDao.open();
                                       byte[] avatarCurrent =studentDao.findMyAvatar(user);
                                       if(Arrays.equals(avatarCurrent, avatarSelectat.getImage())){
                                           studentDao.updateAvatar(userAvatars.get(0).getImage(),user);
                                       }
                                       studentDao.close();
                                       initControllers(userAvatars);

                                   } else {
                                       Toast.makeText(MyAvatarsActivity.this, getString(R.string.myavatars_toast_delete_failed), Toast.LENGTH_LONG).show();
                                   }
                               }
                           }
                       })
                       .setNegativeButton(getString(R.string.myavatars_toast_delete_nu), new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               Toast.makeText(MyAvatarsActivity.this,getString(R.string.myavatars_toast_delete_rejected), Toast.LENGTH_LONG).show();
                           }
                       });
               builder.create().show();

               return true;

           }
       };
    }
    @SuppressLint("SetTextI18n")
    private void initControllers(ArrayList<Avatar> userAv){
        Bitmap btm=null;
        for(int i=0;i< imageViewsAvatarList.size();i++){
            imageViewsAvatarList.get(i).setImageBitmap(null);
            imageViewsAvatarList.get(i).setEnabled(false);
        }
        for(int i=0;i< textViewsNameList.size();i++){
            textViewsNameList.get(i).setText("");
        }
        if(userAv.size()!=0){
            for(int i=0;i<userAv.size();i++){
                btm=BitmapFactory.decodeByteArray(userAv.get(i).getImage(),0,userAv.get(i).getImage().length);
                imageViewsAvatarList.get(i).setImageBitmap(Bitmap.createBitmap(btm));
                textViewsNameList.get(i).setText(userAv.get(i).getName());
                imageViewsAvatarList.get(i).setEnabled(true);
            }
        }
        Bitmap btmCurrent=null;
        studentDao.open();
        byte[] avatarCurrent =studentDao.findMyAvatar(user);
        studentDao.close();
        btmCurrent=BitmapFactory.decodeByteArray(avatarCurrent,0,avatarCurrent.length);
        avatarprincipal.setImageBitmap(Bitmap.createBitmap(btmCurrent));
        studentDao.open();
            tvScore.setText(studentDao.findScoreByUser(user).toString()+getString(R.string.my_avatars_no_points));
        studentDao.close();
    }
    private View.OnClickListener changeName(int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivSelected = position;
                if (userAvatars.get(position).getAppAvatar() == 0) {
                    Intent intent = new Intent(getApplicationContext(), UpdateAvatarNameActivity.class);
                    intent.putExtra(Constants.CHANGE_NAME_KEY, textViewsNameList.get(position).getText().toString());
                    startActivityForResult(intent, Constants.UPDATE_AVATAR_REQUEST_CODE);
                } else {
                    Toast.makeText(getApplicationContext(),getString(R.string.myavatars_toast_dont_change), Toast.LENGTH_LONG).show();
                }
            }


        };
    }

    private View.OnClickListener uploadImage(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(userAvatars.size()>=3){
                    Toast.makeText(getApplicationContext(),getString(R.string.myavatars_toast_delete_one),Toast.LENGTH_LONG).show();
                }
                else {
                    Double score;
                    studentDao.open();
                    score = studentDao.findScoreByUser(user);
                    studentDao.close();
                    if(score>=500){
                        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, Constants.UPLOAD_IMAGE_REQUEST_CODE);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),getString(R.string.not_enough_points),Toast.LENGTH_LONG).show();
                    }

                }
            }
        };
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {       Bitmap mBitmap=null;
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode==Constants.UPLOAD_IMAGE_REQUEST_CODE)
        {
            Uri chosenImageUri = data.getData();
            Avatar avatar= new Avatar(null);
            Long id=-Long.parseLong("-1");
            try {
                mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), chosenImageUri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                avatar = new Avatar(byteArray);
                avatarDAO.open();
                 id = avatarDAO.insertAvatarInDatabase(avatar);
                avatarDAO.close();
                avatar.setId(id);
                if (id != -1) {
                    associativeDAO.open();
                    associativeDAO.insertAssociativeAvatar(user, id);
                    associativeDAO.close();
                    studentDao.open();
                    studentDao.updateScore(-500,user);
                    studentDao.close();
                }
                refreshListAvatar();
                initControllers(userAvatars);

            }catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),getString(R.string.myavatars_toast_upload_failed),Toast.LENGTH_LONG).show();
            }
            catch(Exception e){
                avatarDAO.open();
                avatarDAO.deleteAvatarFromPhone(id);
                avatarDAO.close();
                associativeDAO.open();
                associativeDAO.deleteAvatarById(id,user);
                associativeDAO.close();
                refreshListAvatar();
                initControllers(userAvatars);
                Toast.makeText(getApplicationContext(), getString(R.string.myavatars_toast_upload_failed),Toast.LENGTH_LONG).show();
            }

            }


        else if(resultCode==RESULT_OK && requestCode==Constants.UPDATE_AVATAR_REQUEST_CODE && data!=null){
            String newName= data.getStringExtra(Constants.SET_NAME_KEY);
            avatarDAO.open();
            avatarDAO.updatePhoneAvatar(newName,userAvatars.get(ivSelected).getId() );
            avatarDAO.close();
            refreshListAvatar();
            initControllers(userAvatars);

        }


    }
    private void refreshListAvatar(){
        userAvatars=new ArrayList<>();
        associativeDAO.open();
        avatarsIdForUser=associativeDAO.findAllAvatarsId(user);
        associativeDAO.close();
        avatarDAO.open();
        for(int i=0; i< avatarsIdForUser.size();i++){
            userAvatars.add(avatarDAO.findAvatarById(avatarsIdForUser.get(i)));
        }
        avatarDAO.close();
    }
}
