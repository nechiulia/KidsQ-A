package com.example.docta.myapplication.clase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SetIntrebariParser implements Serializable {

    public static SetIntrebari fromJson(String json) throws JSONException {
        if(json==null){
            return null;
        }
        JSONObject object= new JSONObject(json);
        List<Intrebare> usor= getIntrebariListFromJsonArray(object.getJSONArray("usor"));
        List<Intrebare> mediu= getIntrebariListFromJsonArray(object.getJSONArray("mediu"));
        List<Intrebare> greu= getIntrebariListFromJsonArray(object.getJSONArray("greu"));
        return new SetIntrebari (usor,mediu,greu);

    }
    private static OptiuniIntrebare getOptiuniFromJsonObject(JSONObject object)throws JSONException{
        if(object==null){
            return  null;
        }
        String categorie= object.getString("categorie");
        String imagine= object.getString("imagine");
        Double punctaj=object.getDouble("punctaj");
        return new OptiuniIntrebare(categorie,imagine,punctaj);

    }

    private static Intrebare getIntrebareFromJsonObject(JSONObject object) throws JSONException {
        if(object==null){
        return null;
    }
        String  textIntrebare= object.getString("intrebare");
        List<Raspuns> raspunsuri= new ArrayList<>();
        JSONArray array= object.getJSONArray("raspunsuri");
        if(array!=null) {
            for (int i = 0; i < array.length(); i++) {
                JSONObject raspunsJson= array.getJSONObject(i);
                if(raspunsJson!=null) {
                    String textRaspuns = raspunsJson.getString("raspuns");
                    boolean corect = raspunsJson.getBoolean("corect");
                    raspunsuri.add(new Raspuns(textRaspuns, corect));
                }

            }
        }
        OptiuniIntrebare optiuni= getOptiuniFromJsonObject(object.getJSONObject("optiuni"));


        return new Intrebare(textIntrebare,optiuni,raspunsuri);
    }
    private static List<Intrebare> getIntrebariListFromJsonArray(JSONArray array) throws JSONException {
        if(array==null){
            return  null;
        }
        List<Intrebare> intrebari= new ArrayList<>();
        for(int i=0;i<array.length();i++){
            Intrebare intrebare= getIntrebareFromJsonObject(array.getJSONObject(i));
            if(intrebare!=null){
                intrebari.add(intrebare);
            }
        }
        return intrebari;
    }

}
