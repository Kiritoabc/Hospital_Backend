package com.example.hospital.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hospital.entity.domain.Doctor;
import com.example.hospital.entity.domain.User;
import com.example.hospital.entity.dto.RegisterDTO;
import com.example.hospital.entity.result.Result;
import com.example.hospital.exception.AppHttpCodeEnum;
import com.example.hospital.mapper.DoctorMapper;
import com.example.hospital.service.UserService;
import com.example.hospital.mapper.UserMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author kirit
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-07-02 11:24:58
*/
@Transactional
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Autowired
    DoctorMapper doctorMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer login(User user) {
        String phone = user.getPhone();
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone,user.getPhone());
        User user1 = userMapper.selectOne(wrapper);
//        User user = userMapper.selectById(1);
        if(user1 == null || !user1.getPassword().equals(user.getPassword())){
            return null;
        }
        //医生id获取
        LambdaQueryWrapper<Doctor> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.eq(Doctor::getPhone,phone);
        Doctor doctor = doctorMapper.selectOne(wrapper2);
        if(doctor == null){
            return null;
        }
        return doctor.getId();
    }

    @Override
    public Result register(RegisterDTO dto) {
        //判断手机号是否被占用
        LambdaQueryWrapper<User> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper2.eq(User::getPhone,dto.getPhone());
        long count = this.count(lambdaQueryWrapper2);
        if(count!=0){
            return Result.error().message(AppHttpCodeEnum.PHONE_IS_OCCUPIED);
        }
        //保存注册信息
        User user = new User();
        user.setPassword(dto.getPassword());
        user.setPhone(dto.getPhone());
        this.save(user);
        return Result.ok().message("注册成功");
    }

    @Override
    public Result add(String phone) {
        User user = new User();
        user.setPhone(phone);
        user.setPassword("123456789");
        return Result.ok().message("用户添加成功");
    }

    @Override
    public Result deleteByPhone(String phone) {

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getPhone,phone);
        userMapper.delete(lambdaQueryWrapper);
        return null;
    }

    @Override
    public Result updatePhone(String phone, String doctorPhone) {
        LambdaQueryWrapper<User> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper2.eq(User::getPhone,phone);
        User user = list(lambdaQueryWrapper2).get(0);

        user.setPhone(doctorPhone);

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",user.getId());
        userMapper.update(user,updateWrapper);

        return null;
    }

}




