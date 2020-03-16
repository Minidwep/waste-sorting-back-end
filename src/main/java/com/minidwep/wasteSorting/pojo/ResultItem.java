package com.minidwep.wasteSorting.pojo;

public class ResultItem {
    String score;
    String root;
    String keyword;

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

    @Override
    public String toString() {
        return "ResultItem{" +
                "score='" + score + '\'' +
                ", root='" + root + '\'' +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}
