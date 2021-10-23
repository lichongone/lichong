package com.example.one.controller.auth;


import com.example.one.entity.ReturnModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LC
 * @since 2021-08-04
 */
@RestController
public class RoleController {
    private final Logger logger= LoggerFactory.getLogger(RoleController .class);
   @RequestMapping("/add")
    public ReturnModel addRole(HttpServletResponse response){

       System.out.println("进来了");
       return new ReturnModel();

    }
}
