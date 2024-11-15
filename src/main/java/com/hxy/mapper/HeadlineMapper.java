package com.hxy.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hxy.pojo.Headline;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxy.pojo.vo.PortalVo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @author 大宝
* @description 针对表【news_headline】的数据库操作Mapper
* @createDate 2024-11-08 21:35:40
* @Entity com.hxy.pojo.Headline
*/
public interface HeadlineMapper extends BaseMapper<Headline> {
    IPage<Map> selectMyPage(IPage page,@Param("portalVo") PortalVo portalVo);

    Map queryDetailMap(Integer hid);
}




