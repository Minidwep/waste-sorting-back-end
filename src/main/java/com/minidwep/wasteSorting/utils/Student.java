package com.minidwep.wasteSorting.utils;

public class Student {
    String score;
    String root;
    String keyword;
    String baike_info;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getBaike_info() {
        return baike_info;
    }

    public void setBaike_info(String baike_info) {
        this.baike_info = baike_info;
    }

    @Override
    public String toString() {
        return "Student{" +
                "score='" + score + '\'' +
                ", root='" + root + '\'' +
                ", keyword='" + keyword + '\'' +
                ", baike_info='" + baike_info + '\'' +
                '}';
    }
}
