package com.andrzejkarwoski.model;

public class Word {
    private String polish;
    private String english;

    public Word(String polish, String english) {
        this.polish = polish;
        this.english = english;
    }

    public String getPolish() {
        return polish;
    }

    public void setPolish(String polish) {
        this.polish = polish;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    @Override
    public String toString() {
        return polish + ";" + english;
    }
}
