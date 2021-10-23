package com.example.one.controller.app;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.one.entity.Essay;
import com.example.one.entity.Mod;
import com.example.one.entity.Notice;
import com.example.one.service.IEssayService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LC
 * @since 2021-08-07
 */
@RestController
@RequestMapping("/v1_0")
public class EssayController {
    @Autowired
    private IEssayService essayService;
    @GetMapping("/search")
    public Mod searchall(Integer page, Integer per_page, String q, HttpServletResponse response, HttpServletRequest request){
       Mod data=new Mod();
       String header = request.getHeader("Content-Type");
       if(header==null&&!header.equals("application/json")){
           response.setStatus(HttpStatus.SC_BAD_REQUEST);
           data.setMessage("请求参数错误");
           return data;
       }
        String authorization = request.getHeader("Authorization");
       if(authorization==null){
           response.setStatus(HttpStatus.SC_BAD_REQUEST);
           data.setMessage("请求参数错误");
           return data;
       }
        if(page==null){
            page=1;
        }
        if (per_page==null){
            per_page=8;
        }
        IPage<Essay> iPage=new Page<>(page,per_page);
        QueryWrapper<Essay> queryWrapper=new QueryWrapper<Essay>();
        queryWrapper.lambda().like(Essay::getTitle,q);
        iPage=essayService.page(iPage,queryWrapper);
        List<Essay> list=iPage.getRecords();
        Integer total=essayService.count();
        if(list.size()==0){
            response.setStatus(HttpStatus.SC_INSUFFICIENT_STORAGE);
            data.setMessage("数据库错误");
            return data;
        }
        data.setMessage("成功");
        Map map=new HashMap();
        map.put("page",page);
        map.put("per_page",per_page);
        map.put("data",list);
        map.put("total_count",total);
        data.setData(map);
        return data;
      }
    @GetMapping("/suggestion")
    public Mod suggestion( HttpServletRequest request,String q,HttpServletResponse response){
        Mod data=new Mod();
        String header = request.getHeader("Content-Type");
        if(header==null&&!header.equals("application/json")){
            response.setStatus(HttpStatus.SC_BAD_REQUEST);
            data.setMessage("请求参数错误");
            return data;
        }
        if(q==null){
            response.setStatus(HttpStatus.SC_BAD_REQUEST);
            data.setMessage("请求参数错误");
            return data;
        }
        QueryWrapper<Essay> queryWrapper=new QueryWrapper<Essay>();
        queryWrapper.lambda().like(Essay::getTitle,q);
        List<Essay> list = essayService.list(queryWrapper);
        List<String> collect = list.stream().map(e -> e.getTitle()).collect(Collectors.toList());
        Map map=new HashMap();
        map.put("options",collect.toArray());
        data.setMessage("成功");
        data.setData(map);
        return data;
    }
}
