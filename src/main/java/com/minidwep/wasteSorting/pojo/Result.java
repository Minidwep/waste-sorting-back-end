package com.minidwep.wasteSorting.pojo;

import java.util.List;

public class Result {
    String log_id;
    String result_num;
    List<ResultItem> result;

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public String getResult_num() {
        return result_num;
    }

    public void setResult_num(String result_num) {
        this.result_num = result_num;
    }
    public List<ResultItem> getResult() {
        return result;
    }

    public void setResult(List<ResultItem> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Result{" +
                "log_id='" + log_id + '\'' +
                ", result_num='" + result_num + '\'' +
                ", results=" + result +
                '}';
    }
}
