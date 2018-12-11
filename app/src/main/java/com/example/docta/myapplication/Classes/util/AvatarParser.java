package com.example.docta.myapplication.Classes.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;


public class AvatarParser implements Serializable {
    private static ArrayList<Avatar> avatarss = new ArrayList<>();

    public static ArrayList<Avatar> getAvatarss() {
        return avatarss;
    }

    public static void setAvatarss(ArrayList<Avatar> avatarss) {
        AvatarParser.avatarss = avatarss;
    }

    private static ArrayList<Avatar> getLogoImage(String name, Double price , String url, long appAvatar){
        ArrayList<Avatar> appAvatars=new ArrayList<>();
        try {
          appAvatars= new ImageDownload().execute(name,price,url,appAvatar).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appAvatars;
    }

    public static ArrayList<Avatar> fromJson(String json) throws JSONException {
        ArrayList<Avatar> appAvatars= new ArrayList<>();
        if(json!=null){
            JSONArray array = new JSONArray(json);
            for(int i = 0; i<array.length(); i++){
                appAvatars=getAvatarFromJson(array.getJSONObject(i));
            }
        }
        return appAvatars;

    }
    public static ArrayList<Avatar> getAvatarFromJson(JSONObject object) throws JSONException {
        ArrayList<Avatar> appAvatars= new ArrayList<>();
        if (object!=null) {
            String name = object.getString("name");
            Double price = object.getDouble("price");
            String urlImage = object.getString("image");
            long appAvatar = object.getInt("app_avatar");
            appAvatars=getLogoImage(name, price, urlImage, appAvatar);
        }
        return appAvatars;

    }

}


class ImageDownload extends AsyncTask<Object, Void,ArrayList<Avatar>>
{

    Bitmap bmp=null;
    @Override
    protected void onPreExecute() {
 
        super.onPreExecute();

        //ShowDialog();
    }

    @Override
    protected ArrayList<Avatar> doInBackground(Object... params)
    {   ArrayList<Avatar> avatars=AvatarParser.getAvatarss();
        byte[] avatar = new byte[0];
        try {
            URL urlSDADAD = new URL(params[2].toString());
            bmp = BitmapFactory.decodeStream(urlSDADAD.openConnection().getInputStream());
            ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();

            bmp.compress(Bitmap.CompressFormat.PNG,100,byteArrayStream);
            avatar = byteArrayStream.toByteArray();

            Avatar a = new Avatar(params[0].toString(),Double.valueOf(params[1].toString()),avatar, Long.parseLong(params[3].toString()));
            avatars.add(a);

        } catch (IOException e) {
            e.printStackTrace();
        }
        AvatarParser.setAvatarss(avatars);
        return AvatarParser.getAvatarss();
    }

    @Override
    protected void onPostExecute(ArrayList<Avatar> result)
    {
        super.onPostExecute(result);
    }

}