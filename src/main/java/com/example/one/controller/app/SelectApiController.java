package com.example.one.controller.app;

import com.example.one.entity.AppMapper;
import com.example.one.entity.ReturnModel;
import org.omg.PortableInterceptor.RequestInfo;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/app")
public class SelectApiController {
    @GetMapping("/")
    public List selectApi(HttpServletRequest request){
        ReturnModel returnModel=new ReturnModel();
        List<AppMapper> list=null;
        ServletContext servletContext=request.getSession().getServletContext();
        if(servletContext==null){
            return null;
        }
        WebApplicationContext applicationContext=WebApplicationContextUtils.getWebApplicationContext(servletContext);
        Map<String, HandlerMapping> allRequestMappings= BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext,HandlerMapping.class,true,false);
        allRequestMappings.values().stream().filter(i->i instanceof RequestMappingHandlerMapping).forEach(e->{
                RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) e;
                Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();
                handlerMethodMap.entrySet().forEach(a -> {
                    //获取实体对象
                    RequestMappingInfo requestInfo = a.getKey();
                    HandlerMethod handlerMethod = a.getValue();
                    //获取请求的url
                    PatternsRequestCondition patternsRequestCondition = requestInfo.getPatternsCondition();
                    Iterator<String> iterator = patternsRequestCondition.getPatterns().iterator();
                    String requestUrl = iterator.hasNext() ? iterator.next() : "";
                    System.out.println(requestUrl+"11111111");
                    //获取接口所在的类名，方法名，参数名
                    String comtrollername = handlerMethod.getBean().toString();
                    String requestMethodName = handlerMethod.getMethod().getName();//selectAPI

                });
            }


        );

                    return list;

            }

    }