package com.minidwep.wasteSorting.bean;

public class Question {
    private Integer id;

    private String info;

    private Integer answer;

    private Integer numTrue;

    private Integer numFalse;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    public Integer getNumTrue() {
        return numTrue;
    }

    public void setNumTrue(Integer numTrue) {
        this.numTrue = numTrue;
    }

    public Integer getNumFalse() {
        return numFalse;
    }

    public void setNumFalse(Integer numFalse) {
        this.numFalse = numFalse;
    }
}