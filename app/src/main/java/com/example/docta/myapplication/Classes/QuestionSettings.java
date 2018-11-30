package com.example.docta.myapplication.Classes;

import java.io.Serializable;

public class QuestionSettings implements Serializable {
    private String category;
    private String image;
    private Double score;

    public QuestionSettings(String category, String image, Double score) {
        this.category = category;
        this.image = image;
        this.score = score;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "QuestionSettings{" +
                "category='" + category + '\'' +
                ", image='" + image + '\'' +
                ", score=" + score +
                '}';
    }

}
