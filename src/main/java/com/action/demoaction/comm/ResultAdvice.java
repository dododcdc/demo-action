package com.action.demoaction.comm;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Component
@ControllerAdvice
public class ResultAdvice implements ResponseBodyAdvice<Object>  {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        // BookAction 这个类下面的都会调用 下面的beforeBodyWrite
        if("com.action.demoaction.advice.BookAction".equals(methodParameter.getMethod().getDeclaringClass().getName())){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        System.out.println("ResultAdvice hello");
        return null;
    }
}
