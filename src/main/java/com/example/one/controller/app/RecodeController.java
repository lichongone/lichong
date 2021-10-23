package com.example.one.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.one.entity.Essay;
import com.example.one.entity.Mod;
import com.example.one.entity.Notice;
import com.example.one.entity.Recode;
import com.example.one.service.INoticeService;
import com.example.one.service.IRecodeService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

@RestController
@RequestMapping("/app/v1_0/search/histories")
public class RecodeController {
    @Autowired
    private IRecodeService recodeService;
    @RequestMapping(value = "",method= RequestMethod.DELETE)
    public Mod delect(HttpServletRequest request, HttpServletResponse response){
        Mod data=null;
        String string=request.getHeader("Content-Type");
        String auth=request.getHeader("Authorization");
        if(string==null||!string.equals("application/x-www-form-urlencoded")){
            response.setStatus(HttpStatus.SC_BAD_REQUEST);
            return data;
        }else  if(auth==null){
            response.setStatus(HttpStatus.SC_BAD_REQUEST);
            return data;
        }
        QueryWrapper<Recode> noticeQueryWrapper=new QueryWrapper<>();
        noticeQueryWrapper.lambda().eq(Recode::getUserId, auth);
        boolean remove = recodeService.remove(noticeQueryWrapper);
        if(remove==false){
            response.setStatus(HttpStatus.SC_INSUFFICIENT_STORAGE);
        }
        response.setStatus(HttpStatus.SC_NO_CONTENT);
        return data;
    }
    @RequestMapping(value = "",method= RequestMethod.GET)
    public Mod select(HttpServletRequest request, HttpServletResponse response){
        Mod data=null;
        String string=request.getHeader("application/json");
        String auth=request.getHeader("Authorization");
        if(string==null||!string.equals("application/x-www-form-urlencoded")){
            response.setStatus(HttpStatus.SC_INSUFFICIENT_STORAGE);
            return data;
        }else  if(auth==null){
            response.setStatus(HttpStatus.SC_INSUFFICIENT_STORAGE);
            return data;
        }
        QueryWrapper<Recode> noticeQueryWrapper=new QueryWrapper<>();
        noticeQueryWrapper.lambda().eq(Recode::getUserId,auth);
        List<Recode> list=recodeService.list(noticeQueryWrapper);
        data.setMessage("成功");
        data.setData(list);
        return data;
    }
}