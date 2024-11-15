package com.hxy.controller;

import com.hxy.pojo.Type;
import com.hxy.pojo.vo.PortalVo;
import com.hxy.service.HeadlineService;
import com.hxy.service.TypeService;
import com.hxy.service.UserService;
import com.hxy.utils.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("portal")
public class PortalController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private HeadlineService headlineService;

    /**
     * 查询首页分类的业务
     * @return
     */
    @GetMapping("findAllTypes")
    public Result findAllTypes(){
        List<Type> list = typeService.list();
        return Result.ok(list);
    }
    /**
     * 首页分页查询
     * @return
     */
    @PostMapping("findNewsPage")
    public Result findNewPage(@RequestBody PortalVo portalVo){
        Result result = headlineService.findNewPage(portalVo);
        return result;
    }
    @PostMapping("showHeadlineDetail")
    public Result showHeadlineDetail(Integer hid){
        Result result = headlineService.showHeadlineDetail(hid);
        return result;
    }

}
