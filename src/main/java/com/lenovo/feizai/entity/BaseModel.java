package com.lenovo.feizai.entity;


import lombok.Data;

import java.util.List;

@Data
public class BaseModel<T> {
    int code;
    String message;
    T data;
    List<T> datas;

//    @Override
//    public String toString() {
//        return "BaseModel{" +
//                "code=" + code +
//                ", message='" + message + '\'' +
//                ", data=" + data +
//                ", datas=" + datas +
//                '}';
//    }
//
//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public T getData() {
//        return data;
//    }
//
//    public void setData(T data) {
//        this.data = data;
//    }
//
//    public List<T> getDatas() {
//        return datas;
//    }
//
//    public void setDatas(List<T> datas) {
//        this.datas = datas;
//    }
}
