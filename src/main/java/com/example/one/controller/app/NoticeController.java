package com.example.one.controller.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.one.entity.Mod;
import com.example.one.entity.Notice;
import com.example.one.service.INoticeService;
import org.apache.http.HttpStatus;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app/v1_0/announcements")
public class NoticeController {
    @Autowired
    private INoticeService noticeService;
    @GetMapping(value = "/{id}")
    public Mod getNoiticOne(@PathVariable String id, HttpServletResponse response){
        System.out.println("进来了");
        System.out.println(id);
        Mod data=new Mod();
        if(id==null){
            response.setStatus(HttpStatus.SC_BAD_REQUEST);
            data.setMessage("参数错误");
            return data;
        }
        Notice list=noticeService.getById(id);
        if(list==null){
            if(id==null){
            response.setStatus(HttpStatus.SC_INSUFFICIENT_STORAGE);
            data.setMessage("数据库错误");
            return data;
        }
        }
        data.setMessage("成功");
        data.setData(list);

        return data;
    }
    @GetMapping(value = "")
    public Mod all( Integer page,Integer per_page,HttpServletResponse response){
        System.out.println("22222222222");
        Mod data=new Mod();
        if(page==null){
            page=1;
        }
        if (per_page==null){
            per_page=8;
        }
        IPage<Notice> iPage=new Page<>(page,per_page);
        iPage=noticeService.page(iPage);
        List<Notice> list=iPage.getRecords();
        Integer total=noticeService.count();
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
}
