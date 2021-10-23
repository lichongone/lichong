package com.example.one.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnModel {
    private String status;
    private String code;
    private Object data;


    public  ReturnModel newSuccessInstance(Object o){
        ReturnModel model=new ReturnModel();
        model.setStatus("0");
        model.setCode("成功");
        model.setData(o);
        return model;
    }

     public ReturnModel newFailInstance(Object o){
        ReturnModel model=new ReturnModel();
        model.setStatus("1");
        model.setCode("失败");
        model.setData(o);
        return model;
    }

    }








