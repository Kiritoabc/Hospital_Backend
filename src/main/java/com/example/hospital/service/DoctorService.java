package com.example.hospital.service;

import com.example.hospital.entity.domain.Doctor;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hospital.entity.dto.DoctorDTO;
import com.example.hospital.entity.dto.DoctorSearchDto;
import com.example.hospital.entity.dto.RegisterDTO;
import com.example.hospital.entity.result.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
* @author kirit
* @description 针对表【doctor】的数据库操作Service
* @createDate 2023-07-01 09:56:10
*/
public interface DoctorService extends IService<Doctor> {
    List<String> setRoles(Object loginId);

    Map<String, Object> info(Object loginId);

    Result updateAvatar(MultipartFile multipartFile);

    Result register(RegisterDTO dto);

    Result search(DoctorSearchDto dto);

    Result add(RegisterDTO dto);

    Result deleteById(int id);

    Result update(DoctorDTO doctor);
}
