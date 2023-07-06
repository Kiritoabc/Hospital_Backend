package com.example.hospital.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hospital.entity.domain.Room;
import com.example.hospital.mapper.RoomMapper;
import com.example.hospital.service.RoomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
* @author kirit
* @description 针对表【room】的数据库操作Service实现
* @createDate 2023-07-01 09:56:10
*/
@Transactional
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room>
    implements RoomService {

    @Override
    public Map<String, Object> all(String searchName, Integer pageNum, Integer size, Integer roomId, Integer departId) {
        LambdaQueryWrapper<Room> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        Map<String, Object> data = new HashMap<>();
        lambdaQueryWrapper
                .eq(!Objects.isNull(searchName),Room::getRoomName,searchName)
                .eq(!Objects.isNull(roomId),Room::getRoomId,roomId)
                .eq(!Objects.isNull(departId),Room::getDepartId,departId);
        Page<Room> page =new Page<>(pageNum,size);
        IPage<Room> ipage = this.baseMapper.selectPage(page,lambdaQueryWrapper);
        data.put("items",ipage);
        return data;
    }
}




