package com.minidwep.wasteSorting.bean;

public class Feedback {
    private Integer id;
    private Integer uId;
    private Integer type;
    private String rubName;
    private Integer status;
    private Integer weight;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUId() {
        return uId;
    }

    public void setUId(Integer uId) {
        this.uId = uId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRubName() {
        return rubName;
    }

    public void setRubName(String rubName) {
        this.rubName = rubName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "feedback{" +
                "id=" + id +
                ", uId=" + uId +
                ", type=" + type +
                ", rubName='" + rubName + '\'' +
                ", status=" + status +
                ", weight=" + weight +
                '}';
    }
}
