package com.example.hospital.mapper;

import com.example.hospital.entity.domain.Depart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author kirito
* @description 针对表【depart】的数据库操作Mapper
* @createDate 2023-07-01 09:56:10
* @Entity generator.domain.Depart
*/
@Mapper
public interface DepartMapper extends BaseMapper<Depart> {

    @Select("select depart_id from depart")
    List<Integer> selectIdList();

}




