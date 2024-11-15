package com.hxy.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hxy.utils.JwtHelper;
import com.hxy.utils.Result;
import com.hxy.utils.ResultCodeEnum;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录保护拦截器，检查请求头中token是否有效
 *  有效 放行
 *  无效 拦截 返回504
 */
@Component
public class LoginProtectedInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtHelper jwtHelper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取token
        String token = request.getHeader("token");
        // 检查是否有效
        boolean expiration = jwtHelper.isExpiration(token);
        // 有效 放行
        if (!expiration){
            return true;
        }
        // 无效 返回504 拦截
        Result result = Result.build(null, ResultCodeEnum.NOTLOGIN);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(result);
        response.getWriter().print(json);
        return false;
    }
}
