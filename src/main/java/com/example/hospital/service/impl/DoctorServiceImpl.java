package com.example.hospital.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hospital.entity.domain.Doctor;
import com.example.hospital.entity.dto.DoctorDTO;
import com.example.hospital.entity.dto.DoctorSearchDto;
import com.example.hospital.entity.dto.RegisterDTO;
import com.example.hospital.entity.result.Result;
import com.example.hospital.mapper.DoctorMapper;
import com.example.hospital.service.DoctorService;
import com.example.hospital.service.UserService;
import com.example.hospital.util.MinioUtils;
import com.example.hospital.util.SnowFlake;
import com.mysql.cj.util.StringUtils;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.*;

/**
* @author kirit
* @description 针对表【doctor】的数据库操作Service实现
* @createDate 2023-07-01 09:56:10
*/
@Transactional
@Service
public class DoctorServiceImpl extends ServiceImpl<DoctorMapper, Doctor>
    implements DoctorService {

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private MinioUtils minioUtils;

    @Value("${minio.bucketName}")
    public  String BUCKET_NAME;

    @Value("${avatar}")
    public String avatarURL;

    @Autowired
    private UserService userService;

    @Override
    public List<String> setRoles(Object loginId) {
//        String name = loginId;
        LambdaQueryWrapper<Doctor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Doctor::getId,loginId);
        Doctor loginDoctor = this.baseMapper.selectOne(wrapper);
        int role= loginDoctor.getRole();
        List roles = new ArrayList<>();
        switch (role){
            case 0:
                roles.add("doctor");
                break;
            case 1:
                roles.add("admin");
                break;
            case 2:
                roles.add("admin");
                break;
        }
        return roles;
    }

    @Override
    public Map<String, Object> info(Object loginId) {
        Map<String, Object> data = new HashMap<>();
        Doctor doctor = this.baseMapper.selectById((Serializable) loginId);
        data.put("doctorVo",doctor);
        return data;
    }

    @SneakyThrows
    @Override
    public Result updateAvatar(MultipartFile multipartFile) {

        int id = Integer.parseInt(StpUtil.getLoginId().toString());
//        int id = 1;
        String fileName = multipartFile.getOriginalFilename();
        String url = minioUtils.uploadFile(multipartFile,BUCKET_NAME, id +"_avatar"+fileName);
        LambdaUpdateWrapper<Doctor> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Doctor::getId, id).set(Doctor::getAvatar, url);
        doctorMapper.update(null, lambdaUpdateWrapper);

        Map map = new HashMap<>();
        map.put("url",url);

        return Result.ok().message("头像修改成功").data(map);
    }

    @Override
    public Result register(RegisterDTO dto){

        Doctor doctor = new Doctor();
        BeanUtils.copyProperties(dto,doctor);
        //设置UUID
        SnowFlake snowFlake = new SnowFlake(1,1,1);
        doctor.setUuid(String.valueOf(snowFlake.nextId()));
        //设置默认权限(默认为普通用户"user")
        doctor.setRole(0);
        //设置头像
        doctor.setAvatar(avatarURL);
        this.save(doctor);
        return Result.ok().message("注册成功");
    }

    @Override
    public Result search(DoctorSearchDto dto) {
        LambdaQueryWrapper<Doctor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(!StringUtils.isNullOrEmpty(dto.getName()),Doctor::getName,dto.getName())
                .eq(!Objects.isNull(dto.getId()),Doctor::getId,dto.getId())
                .eq(!StringUtils.isNullOrEmpty(dto.getIdNum()),Doctor::getIdNum,dto.getIdNum())
                .eq(!StringUtils.isNullOrEmpty(dto.getPhone()),Doctor::getPhone,dto.getPhone())
                .le(!Objects.isNull(dto.getFee()),Doctor::getFee,dto.getFee())
                .eq(!Objects.isNull(dto.getDepartId()),Doctor::getDepartId,dto.getDepartId())
                .eq(!Objects.isNull(dto.getRole()),Doctor::getRole,dto.getRole())
                .or()
                .eq(!Objects.isNull(dto.getRole()),Doctor::getRole,2);
        Page<Doctor> page = new Page<>(dto.getPageNum(),dto.getPageSize());
        IPage<Doctor> iPage = page(page,lambdaQueryWrapper);
        return Result.ok().data("items",iPage);
    }

    @Override
    public Result add(RegisterDTO dto) {

        Doctor doctor = new Doctor(dto);
        doctor.setAvatar(avatarURL);
        SnowFlake snowFlake = new SnowFlake(1,1,1);
        doctor.setUuid(String.valueOf(snowFlake.nextId()));
        doctor.setRole(0);
        this.save(doctor);
        userService.add(doctor.getPhone());

        return Result.ok().message("添加成功");
    }

    @Override
    public Result deleteById(int id) {

        LambdaUpdateWrapper<Doctor> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Doctor::getId,id);
        Doctor doctor = list(lambdaUpdateWrapper).get(0);

        String phone = doctor.getPhone();
        userService.deleteByPhone(phone);
        doctorMapper.deleteById(id);
        return Result.ok().message("删除成功");
    }

    @Override
    public Result update(DoctorDTO doctor) {
        LambdaUpdateWrapper<Doctor> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Doctor::getId,doctor.getId());
        Doctor doctor1 = list(lambdaUpdateWrapper).get(0);

        if (!doctor1.getPhone().equals(doctor.getPhone())){
            userService.updatePhone(doctor1.getPhone(),doctor.getPhone());
        }

        doctor1.setName(Objects.isNull(doctor.getName()) ? doctor1.getName(): doctor.getName());
        doctor1.setSex(Objects.isNull(doctor.getSex()) ? doctor1.getSex(): doctor.getSex());
        doctor1.setBirthday(Objects.isNull(doctor.getBirthday()) ? doctor1.getBirthday(): doctor.getBirthday());
        doctor1.setIdNum(Objects.isNull(doctor.getIdNum()) ? doctor1.getIdNum(): doctor.getIdNum());
        doctor1.setPhone(Objects.isNull(doctor.getPhone()) ? doctor1.getPhone(): doctor.getPhone());
        doctor1.setFee(Objects.isNull(doctor.getFee()) ? doctor1.getFee(): doctor.getFee());
        doctor1.setIntroduce(Objects.isNull(doctor.getIntroduce()) ? doctor1.getIntroduce(): doctor.getIntroduce());
        doctor1.setDepartId(Objects.isNull(doctor.getDepartId()) ? doctor1.getDepartId(): doctor.getDepartId());
        doctor1.setRole(Objects.isNull(doctor.getRole()) ? doctor1.getRole(): doctor.getRole());

        UpdateWrapper<Doctor> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",doctor.getId());
        doctorMapper.update(doctor1,updateWrapper);

        return Result.ok().message("更新成功");
    }
}




