package com.example.docta.myapplication.Classes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.docta.myapplication.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.docta.myapplication.util.Global.avatars;

public class AvatarParser {

    private static void getLogoImage(String name,Double price , String url,boolean appAvatar){

        try {
            new ImageDownload().execute(name,price,url,appAvatar);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void fromJson(String json) throws JSONException {
        if(json==null){

        }
        JSONArray array= new JSONArray(json);
        for(int i = 0;i<array.length();i++){
            getAvatarFromJson(array.getJSONObject(i));
        }

    }
    public static void getAvatarFromJson(JSONObject object) throws JSONException {
        if (object!=null) {
            String name = object.getString("name");
            Double price = object.getDouble("price");
            String urlImage = object.getString("image");
            Boolean appAvatar = object.getBoolean("app_avatar");
            getLogoImage(name, price, urlImage, appAvatar);
        }
    }

  /*  private static ArrayList<Avatar> getListAvatarsFromJson(JSONArray array) throws JSONException {
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
    }*/



}


class ImageDownload extends AsyncTask<Object, Void, byte[]>
{

    Bitmap bmp=null;
    @Override
    protected void onPreExecute() {
 
        super.onPreExecute();

        //ShowDialog();
    }

    @Override
    protected byte[] doInBackground(Object... params)
    {
        byte[] avatar = new byte[0];
        try {
            URL urlSDADAD = new URL(params[2].toString());
            bmp = BitmapFactory.decodeStream(urlSDADAD.openConnection().getInputStream());
            ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();

            bmp.compress(Bitmap.CompressFormat.PNG,100,byteArrayStream);
            avatar = byteArrayStream.toByteArray();

            Avatar a = new Avatar(params[0].toString(),Double.valueOf(params[1].toString()),avatar,Boolean.getBoolean(params[3].toString()));
            avatars.add(a);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return avatar;

    }

    @Override
    protected void onPostExecute(byte[] result)
    {
        super.onPostExecute(result);
    }

}