package com.hxy.service;

import com.hxy.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hxy.utils.Result;

/**
* @author 大宝
* @description 针对表【news_user】的数据库操作Service
* @createDate 2024-11-08 18:03:13
*/
public interface UserService extends IService<User> {

    /**
     * 登录业务
     * @param user
     * @return
     */
    Result login(User user);

    /**
     * 根据token获取用户数据
     * @param token
     * @return
     */
    Result getUserInfo(String token);

    /**
     * 检查用户名是否可用
     * @param username
     * @return
     */
    Result checkUserName(String username);

    /**
     * 用户注册业务
     * @param user
     * @return
     */
    Result regist(User user);

}
