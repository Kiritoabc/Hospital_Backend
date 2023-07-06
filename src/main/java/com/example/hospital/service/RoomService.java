package com.example.hospital.service;

import com.example.hospital.entity.domain.Room;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author kirit
* @description 针对表【room】的数据库操作Service
* @createDate 2023-07-01 09:56:10
*/
public interface RoomService extends IService<Room> {

    Map<String, Object> all(String searchName, Integer pageNum, Integer size, Integer roomId, Integer departId);
}
