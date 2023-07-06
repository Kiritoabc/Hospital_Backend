package com.example.hospital.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hospital.entity.domain.Depart;
import com.example.hospital.mapper.DepartMapper;
import com.example.hospital.service.DepartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
* @author kirito
* @description 针对表【depart】的数据库操作Service实现
* @createDate 2023-07-01 09:56:10
*/
@Transactional
@Service
public class DepartServiceImpl extends ServiceImpl<DepartMapper, Depart>
    implements DepartService {

    @Override
    public Map<String, Object> all(String searchName, Integer pageNum, Integer pageSize, Integer departId) {
        LambdaQueryWrapper<Depart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        Map<String, Object> data = new HashMap<>();
        lambdaQueryWrapper
                .eq(!Objects.isNull(searchName),Depart::getDepartName,searchName)
                .eq(!Objects.isNull(departId),Depart::getDepartId,departId);
        Page<Depart> page =new Page<>(pageNum,pageSize);
        IPage<Depart> ipage = this.baseMapper.selectPage(page,lambdaQueryWrapper);
        data.put("items",ipage);
        return data;
    }
}




