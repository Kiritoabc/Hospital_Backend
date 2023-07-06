package com.example.hospital.mapper;

import com.example.hospital.entity.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author kirit
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-07-02 11:24:58
* @Entity com.example.hospital.entity.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




