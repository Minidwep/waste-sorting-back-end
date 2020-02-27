package com.minidwep.wasteSorting.bean;

public class Rubbish {
    private int id;
    private int type;
    private String rubName;
    private int weight;
    private String score;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRubName() {
        return rubName;
    }

    public void setRubName(String rubName) {
        this.rubName = rubName;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
    @Override
    public String toString() {
        return "Rubbish{" +
                "id=" + id +
                ", type=" + type +
                ", rubName='" + rubName + '\'' +
                ", weight=" + weight +
                '}';
    }
}
