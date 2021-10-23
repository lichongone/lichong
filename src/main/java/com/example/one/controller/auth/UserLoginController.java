package com.example.one.controller.auth;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.one.entity.*;
import com.example.one.service.IPermissionService;
import com.example.one.service.IRolePermissionService;
import com.example.one.service.IUserRoleService;
import com.example.one.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-08-03
 */
@Api(tags = "登录模块")
@RestController
public class UserLoginController {
    private final Logger logger= LoggerFactory.getLogger(UserLoginController.class);

   @Autowired
    private IUserService userService;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IRolePermissionService rolePermissionService;
    @Autowired
    private IPermissionService permissionService;
    @ApiOperation("登录认证")
    @ApiImplicitParam(name="password",value = "密码")
    @RequestMapping(value = "/login")
   public ReturnModel login(HttpServletRequest request, UserAuth userAuth)  {
        System.out.println("11111111");
        ReturnModel returnModel=new ReturnModel();
        if(userAuth==null){
            return returnModel;
        }
        String username=userAuth.getUsername();
        String password=userAuth.getPassword();
        if(username==null||password==null){
            return returnModel;
        }
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<User>();
        userQueryWrapper.lambda().eq(User::getAccount,username).eq(User::getPassword,password);
        User user=userService.getOne(userQueryWrapper);
        if(user==null){
            return returnModel;
        }
        String id=user.getId();
        UserAuth userAuth1=new UserAuth();
        QueryWrapper<UserRole> userRoleQueryWrapper=new QueryWrapper<UserRole>();
        userRoleQueryWrapper.lambda().eq(UserRole::getUserId,id);
        List<UserRole> userRoleList=userRoleService.list(userRoleQueryWrapper);
        if(userRoleList.size()==0){
            return returnModel;
        }
        List<Integer> roleid=userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        QueryWrapper<RolePermission> rolePermissionQueryWrapper=new QueryWrapper<RolePermission>();
        rolePermissionQueryWrapper.lambda().in(RolePermission::getRoleId,roleid);
        List<RolePermission> rolePermissionList=rolePermissionService.list(rolePermissionQueryWrapper);
        if(rolePermissionList.size()==0){
            return returnModel;
        }
        List<Integer> permissionid=rolePermissionList.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
        QueryWrapper<Permission> permissionQueryWrapper=new QueryWrapper<Permission>();
        permissionQueryWrapper.lambda().in(Permission::getId,permissionid);
        List<Permission> permissionList=permissionService.list(permissionQueryWrapper);
        user.setPermissions(permissionList);
        userAuth=UserAuth.create(user);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userAuth,null,
        userAuth.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        System.out.println("进来了");
        return returnModel;
    }
    @RequestMapping("/loginout")
    public ReturnModel login(HttpServletRequest request)  {
        System.out.println("終於寄哪裏了");
        SecurityContextHolder.getContext().setAuthentication(null);
        return new ReturnModel();
    }
}
