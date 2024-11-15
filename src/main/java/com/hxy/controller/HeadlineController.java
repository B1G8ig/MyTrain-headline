package com.hxy.controller;

import com.hxy.pojo.Headline;
import com.hxy.service.HeadlineService;
import com.hxy.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@RestController
@RequestMapping("headline")
@CrossOrigin
public class HeadlineController {
    @Autowired
    private HeadlineService headlineService;

    // 登录以后才可以访问
    @PostMapping("publish")
    public Result publish(@RequestBody Headline headline,@RequestHeader String token){
        Result result = headlineService.publish(headline,token);
        return result;
    }

    /**
     * 头条回显业务
     * @param hid
     * @return
     */
    @PostMapping("findHeadlineByHid")
    public Result findHeadlineByHid(Integer hid){
        Result result = headlineService.findHeadlineByHid(hid);
        return result;
    }

    /**
     * 头条修改业务
     */
    @PostMapping("update")
    public Result update(@RequestBody Headline headline){
        Result result = headlineService.updateHeadline(headline);
        return result;
    }

    /**
     * 头条删除业务
     * @param hid
     * @return
     */
    @PostMapping("removeByHid")
    public Result removeByHid(Integer hid){
        Result result = headlineService.removeByHid(hid);
        return result;
    }
}
