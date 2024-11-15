package com.hxy.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxy.pojo.User;
import com.hxy.service.UserService;
import com.hxy.mapper.UserMapper;
import com.hxy.utils.JwtHelper;
import com.hxy.utils.MD5Util;
import com.hxy.utils.Result;
import com.hxy.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
* @author 大宝
* @description 针对表【news_user】的数据库操作Service实现
* @createDate 2024-11-08 18:03:13
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtHelper jwtHelper;

    /**
     * 登录业务
     *      1.根据账号，查询用户对象
     *      2.如果用户对象为空，查询失败，账号错误    501
     *      3.对比密码  失败 返回 503
     *      4.根据用户id生成token，将token封装到result中
     * @param user
     * @return
     */
    @Override
    public Result login(User user) {
        // 根据账号查询数据
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper();
        wrapper.eq(User::getUsername,user.getUsername());
        User loginUser = userMapper.selectOne(wrapper);
        if (loginUser == null){
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }
        // 对比密码
        if (!StringUtils.isEmpty(user.getUserPwd())
                && MD5Util.encrypt(user.getUserPwd()).equals(loginUser.getUserPwd())){
            // 登录成功，根据用户id生成token，再将token封装到result中返回
            String token = jwtHelper.createToken(Long.valueOf(loginUser.getUid()));
            Map data = new HashMap();
            data.put("token",token);
            return Result.ok(data);

        }
        return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
    }

    /**
     * 根据token获取用户数据
     *      1.token是否在有效期
     *      2.根据token解析出userId
     *      3.根据用户id查询用户数据
     *      4.去掉密码，封装到result中返回
     * @param token
     * @return
     */
    @Override
    public Result getUserInfo(String token) {
        if (jwtHelper.isExpiration(token)){
            // 失效，未登录看待
            return Result.build(null,ResultCodeEnum.NOTLOGIN);
        }
        int id = jwtHelper.getUserId(token).intValue();
        User user = userMapper.selectById(id);
        user.setUserPwd("");
        Map data = new HashMap();
        data.put("loginUser",user);
        return Result.ok(data);
    }

    /**
     * 检查用户名是否可用
     *      1.根据username查询数据
     *      2.如果返回null，则用户名可用
     *      3.如果有返回值，则用户名不可用
     * @param username
     * @return
     */
    @Override
    public Result checkUserName(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,username);
        User user = userMapper.selectOne(wrapper);
        if (user != null){
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }
        return Result.ok(null);
    }

    /**
     * 用户注册业务
     *      1.先检查用户名是否被占用
     *      2.密码加密处理
     *      2.将用户数据存入数据库
     * @param user
     * @return
     */
    @Override
    public Result regist(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,user.getUsername());
        User checkUser = userMapper.selectOne(wrapper);
        if (checkUser != null){
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }
        user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));
        userMapper.insert(user);
        return Result.ok(null);
    }
}




