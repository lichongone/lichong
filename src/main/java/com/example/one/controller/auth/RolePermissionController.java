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
 * @author LC
 * @since 2021-08-04
 */
@RestController
@RequestMapping("/rolepermission")
public class RolePermissionController {
    private final Logger logger= LoggerFactory.getLogger(RolePermissionController.class);
}
