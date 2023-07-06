package com.example.hospital.service;

import com.example.hospital.entity.domain.Depart;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author kirit
* @description 针对表【depart】的数据库操作Service
* @createDate 2023-07-01 09:56:10
*/
public interface DepartService extends IService<Depart> {

    Map<String, Object> all(String searchName, Integer pageNum, Integer pageSize, Integer departId);
}
