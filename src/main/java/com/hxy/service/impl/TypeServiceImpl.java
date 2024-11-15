package com.hxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxy.pojo.Type;
import com.hxy.service.TypeService;
import com.hxy.mapper.TypeMapper;
import com.hxy.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 大宝
* @description 针对表【news_type】的数据库操作Service实现
* @createDate 2024-11-08 18:03:13
*/
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
    implements TypeService{

}




