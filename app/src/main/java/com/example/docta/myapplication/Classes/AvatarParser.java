package com.example.docta.myapplication.Classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.example.docta.myapplication.MyAvatarsActivity;
import com.example.docta.myapplication.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class AvatarParser {
    private static byte[] getLogoImage(String url){
        try {
            byte[] avatar;
            URL imageUrl = new URL(url);
            InputStream stream=imageUrl.openStream();
            Bitmap bmp= BitmapFactory.decodeStream(stream);
            ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG,100,byteArrayStream);
            avatar = byteArrayStream.toByteArray();
            return  avatar;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ArrayList<Avatar> fromJson(String json) throws JSONException {
        if(json==null){
            return null;
        }
        JSONArray array= new JSONArray(json);
        ArrayList<Avatar> avatars = getListAvatarsFromJson(array);
        return avatars;

    }
    private static Avatar getAvatarFromJson(JSONObject object) throws JSONException {
        if (object==null) {
            return null;
        }
        String name = object.getString("name");
        Double price = object.getDouble("price");
        String urlImage= object.getString("image");
        byte[] image= getLogoImage(urlImage);
        Boolean appAvatar= object.getBoolean("app_avatar");

        return new Avatar(name,price,image,appAvatar);
    }

    private static ArrayList<Avatar> getListAvatarsFromJson(JSONArray array) throws JSONException {
        if (array==null){
            return null;
        }
        ArrayList<Avatar> list = new ArrayList<>();
        for (int i = 0 ; i < array.length();i++){
            Avatar avatar = getAvatarFromJson(array.getJSONObject(i));
            if(avatar!=null){
                list.add(avatar);
            }
        }
        return list;
    }



}
