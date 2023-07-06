package com.example.hospital.mapper;

import com.example.hospital.entity.domain.Doctor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hospital.entity.dto.OptionItems;
import com.example.hospital.entity.dto.RolesValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author kirit
* @description 针对表【doctor】的数据库操作Mapper
* @createDate 2023-07-01 09:56:10
* @Entity generator.domain.Doctor
*/
@Mapper
public interface DoctorMapper extends BaseMapper<Doctor> {

    @Select("select\n" +
            "    sum(role = 0) as 'doctor',\n" +
            "    sum(role = 1) as 'admin',\n" +
            "    sum(role = 2) as 'doctorAndAdmin'\n" +
            "from\n" +
            "    doctor;\n")
    RolesValue selectCountRoles();
}




