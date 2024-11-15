package com.hxy.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxy.pojo.Headline;
import com.hxy.pojo.vo.PortalVo;
import com.hxy.service.HeadlineService;
import com.hxy.mapper.HeadlineMapper;
import com.hxy.utils.JwtHelper;
import com.hxy.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 大宝
* @description 针对表【news_headline】的数据库操作Service实现
* @createDate 2024-11-08 21:35:40
*/
@Service
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper, Headline>
    implements HeadlineService{

    @Autowired
    private HeadlineMapper headlineMapper;
    @Autowired
    private JwtHelper jwtHelper;

    /**
     * 首页数据查询
     *      1.进行分页查询
     *      2.分页数据拼接到result中
     * TODO: 1.自定义mapper方法进行分页
     *       2.返回的结果List(Map)
     * @param portalVo
     * @return
     */
    @Override
    public Result findNewPage(PortalVo portalVo) {
        IPage<Map> page = new Page<>(portalVo.getPageNum(), portalVo.getPageSize());
        headlineMapper.selectMyPage(page,portalVo);
        Map data = new HashMap();
        data.put("pageData",page.getRecords());
        data.put("pageNum",page.getCurrent());
        data.put("pageSize",page.getSize());
        data.put("totalPage",page.getPages());
        data.put("totalSize",page.getTotal());
        Map pageInfo = new HashMap();
        pageInfo.put("pageInfo",data);
        return Result.ok(pageInfo);
    }

    /**
     * 根据id查询详请
     *      2.查询对应的数据【多表查询,自定义方法，返回map】
     *      1.修改阅读量 + 1 【version乐观锁】
     * @param hid
     * @return
     */
    @Override
    public Result showHeadlineDetail(Integer hid) {
        Map data = headlineMapper.queryDetailMap(hid);
        Map headlineMap = new HashMap();
        headlineMap.put("headline",data);
        // 修改阅读量 + 1
        Headline headline = new Headline();
        headline.setHid((Integer) data.get("hid"));
        headline.setVersion((Integer) data.get("version"));
        headline.setPageViews((Integer) data.get("pageViews") + 1);
        headlineMapper.updateById(headline);
        return Result.ok(headlineMap);
    }

    /**
     * 发布头条方法
     *      1.补全数据
     * @param headline
     * @return
     */
    @Override
    public Result publish(Headline headline,String token) {
        // 根据token查询用户id
        int userId = jwtHelper.getUserId(token).intValue();
        // 数据装配
        headline.setPublisher(userId);
        headline.setPageViews(0);
        headline.setCreateTime(new Date());
        headline.setUpdateTime(new Date());
        headlineMapper.insert(headline);
        return Result.ok(null);
    }

    /**
     * 头条回显
     *      1.根据id查询头条完整信息返回给前端
     *
     * @param hid
     * @return
     */
    @Override
    public Result findHeadlineByHid(Integer hid) {
        Headline headline = headlineMapper.selectById(hid);
        Map data = new HashMap();
        data.put("headline",headline);
        return Result.ok(data);
    }

    /**
     * 修改头条数据
     *      1.hid查询数据的最新version
     *      2.修改数据的修改时间为当前时间
     *
     * @param headline
     * @return
     */
    @Override
    public Result updateHeadline(Headline headline) {
        Integer version = headlineMapper.selectById(headline.getHid()).getVersion();
        headline.setVersion(version);//乐观锁
        headline.setUpdateTime(new Date());
        headlineMapper.updateById(headline);
        return Result.ok(null);
    }

    /**
     * 删除头条信息
     *      1.根据id删除头条信息，逻辑删除
     * @param hid
     * @return
     */
    @Override
    public Result removeByHid(Integer hid) {
        headlineMapper.deleteById(hid);
        return Result.ok(null);
    }


}




