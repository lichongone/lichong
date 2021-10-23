package com.example.one.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppMapper  {
    private String route_map;
    @JSONField(name="static")
    private String statics;
    @JSONField(name="user.smsverificationcode")
    private String ficationcode;
}
