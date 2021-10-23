package com.example.one.controller.auth;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-08-03
 */
@RestController
@RequestMapping("/user")
public class UserController  {
    private final Logger logger= LoggerFactory.getLogger(UserController .class);
}
