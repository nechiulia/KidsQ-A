package com.example.docta.myapplication.Classes.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class QuestionsSetParser implements Serializable {

    public static QuestionsSet fromJson(String json) throws JSONException {
        if(json==null){
            return null;
        }
        JSONObject object= new JSONObject(json);
        ArrayList<Question> easy=  getQuestionsListFromJsonArray(object.getJSONArray("usor"));
        ArrayList<Question> medium=  getQuestionsListFromJsonArray(object.getJSONArray("mediu"));
        ArrayList<Question> hard=  getQuestionsListFromJsonArray(object.getJSONArray("greu"));
        ArrayList<Question> dailyQuestion=  getQuestionsListFromJsonArray(object.getJSONArray("intrebarea zilei"));

        return new QuestionsSet(easy,medium,hard, dailyQuestion);

    }

    private static QuestionSettings getSettingsFromJsonObject(JSONObject object)throws JSONException{
        if(object==null){
            return  null;
        }
        String category= object.getString("categorie");
        String image= object.getString("imagine");
        Double score=object.getDouble("punctaj");
        return new QuestionSettings(category,image,score);

    }

    private static Question getQuestionFromJsonObject(JSONObject object) throws JSONException {
        if(object==null){
        return null;
    }
        String  questionText= object.getString("intrebare");
        ArrayList<Answer> answers= new ArrayList<>();
        JSONArray array= object.getJSONArray("raspunsuri");
        if(array!=null) {
            for (int i = 0; i < array.length(); i++) {
                JSONObject answerJson= array.getJSONObject(i);
                if(answerJson!=null) {
                    String answerText = answerJson.getString("raspuns");
                    boolean corect = answerJson.getBoolean("corect");
                    answers.add(new Answer(answerText, corect));
                }

            }
        }
        QuestionSettings settings= getSettingsFromJsonObject(object.getJSONObject("optiuni"));


        return new Question(questionText,settings,answers);
    }
    private static ArrayList<Question> getQuestionsListFromJsonArray(JSONArray array) throws JSONException {
        if(array==null){
            return  null;
        }
        ArrayList<Question> questions= new ArrayList<>();
        for(int i=0;i<array.length();i++){
            Question question = getQuestionFromJsonObject(array.getJSONObject(i));
            if(question !=null){
                questions.add(question);
            }
        }
        return questions;
    }

}
