package com.example.hospital.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.example.hospital.entity.dto.DoctorDTO;
import com.example.hospital.entity.dto.DoctorSearchDto;
import com.example.hospital.entity.dto.RegisterDTO;
import com.example.hospital.entity.result.Result;
import com.example.hospital.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author kirit
 * @version 1.0
 * @description: TODO
 * @date 2023/7/3 8:39
 */
@RestController
@RequestMapping("/doctor")
@CrossOrigin
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/list")
    public Result All(@RequestBody DoctorSearchDto dto){
        return doctorService.search(dto);
    }

    @SaCheckPermission(value = {"admin"},mode = SaMode.OR)
    @PostMapping("/add")
    public Result add(@RequestBody RegisterDTO dto){
        return doctorService.add(dto);
    }

    @SaCheckPermission(value = {"admin"},mode = SaMode.OR)
    @PostMapping("/delete")
    public Result delete(@RequestParam(name = "id") int id){
        return doctorService.deleteById(id);
    }


    @PostMapping("/update")
    public Result update(@RequestBody DoctorDTO doctorDTO){
        return doctorService.update(doctorDTO);
    }

}