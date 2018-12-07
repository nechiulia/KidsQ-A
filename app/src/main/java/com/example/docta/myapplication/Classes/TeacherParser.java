package com.example.docta.myapplication.Classes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class TeacherParser implements Serializable {

    public static TeacherSet fromJson(String json) throws JSONException {
        if(json==null){
            return null;
        }
        JSONObject object = new JSONObject(json);
        ArrayList<Teacher> listTeacher = getListAccountFromJson(object.getJSONArray("cont_profesor")) ;

        return new TeacherSet(listTeacher);
    }

    private static Teacher getAccountFromJson(JSONObject object) throws JSONException {
        if (object==null) {
            return null;
        }
        String email = object.getString("email");
        String password = object.getString("password");

        return new Teacher(email,password);
    }

    private static ArrayList<Teacher> getListAccountFromJson(JSONArray array) throws JSONException {
        if (array==null){
            return null;
        }
        ArrayList<Teacher> list = new ArrayList<>();
        for (int i = 0 ; i < array.length();i++){
            Teacher teacher = getAccountFromJson(array.getJSONObject(i));
            if(teacher!=null){
                list.add(teacher);
            }
        }
        return list;
    }
}
