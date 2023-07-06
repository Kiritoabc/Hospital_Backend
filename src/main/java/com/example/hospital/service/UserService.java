package com.example.hospital.service;

import com.example.hospital.entity.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hospital.entity.dto.RegisterDTO;
import com.example.hospital.entity.result.Result;

/**
* @author kirit
* @description 针对表【user】的数据库操作Service
* @createDate 2023-07-02 11:24:58
*/
public interface UserService extends IService<User> {
    Integer login(User user);

    Result register(RegisterDTO dto);
    Result add(String phone);

    Result deleteByPhone(String phone);

    Result updatePhone(String phone, String doctorPhone);
}
