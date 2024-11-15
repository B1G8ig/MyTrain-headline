package com.hxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hxy.pojo.Headline;
import com.hxy.pojo.vo.PortalVo;
import com.hxy.utils.Result;

/**
* @author 大宝
* @description 针对表【news_headline】的数据库操作Service
* @createDate 2024-11-08 18:03:13
*/
public interface HeadlineService extends IService<Headline> {


    /**
     * 首页数据查询
     * @param portalVo
     * @return
     */
    Result findNewPage(PortalVo portalVo);

    /**
     * 根据id查询详情
     * @param hid
     * @return
     */
    Result showHeadlineDetail(Integer hid);

    /**
     * 发布头条的方法
     * @param headline
     * @return
     */
    Result publish(Headline headline,String token);

    /**
     * 头条回显
     * @param hid
     * @return
     */
    Result findHeadlineByHid(Integer hid);

    /**
     * 修改头条数据
     * @param headline
     * @return
     */
    Result updateHeadline(Headline headline);

    /**
     * 删除头条信息
     * @param hid
     * @return
     */
    Result removeByHid(Integer hid);
}
