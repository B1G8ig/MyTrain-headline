package com.hxy.controller;

import com.hxy.pojo.User;
import com.hxy.service.UserService;
import com.hxy.utils.JwtHelper;
import com.hxy.utils.MD5Util;
import com.hxy.utils.Result;
import com.hxy.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * controller层只负责接收和返回结果
 * 返回结果的封装和查询在service层中去做
 */
@RestController
@RequestMapping("user")
@CrossOrigin    // 跨域
public class UserController {
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private UserService userService;

    /**
     * 登录业务
     * @param user
     * @return
     */
    @PostMapping("login")
    public Result login(@RequestBody User user){
        Result result = userService.login(user);
        return result;
    }

    /**
     * 根据token获取用户数据
     * @param token
     * @return
     */
    @GetMapping("getUserInfo")
    public Result getUserInfo(@RequestHeader String token){
        Result result = userService.getUserInfo(token);
        return result;
    }

    /**
     * 检查用户名是否可用
     * @param username
     * @return
     */
    @PostMapping("checkUserName")
    public Result checkUserName(String username){
        return userService.checkUserName(username);
    }

    /**
     * 用户注册注册业务
     * @param user
     * @return
     */
    @PostMapping("regist")
    public Result regist(@RequestBody User user){
        return userService.regist(user);
    }

    @GetMapping("checkLogin")
    public Result checkLogin(@RequestHeader String token){
        boolean expiration = jwtHelper.isExpiration(token);
        if (expiration){
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }
        return Result.ok(null);
    }

}
