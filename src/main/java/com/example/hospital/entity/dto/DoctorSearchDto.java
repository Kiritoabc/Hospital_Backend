package com.example.hospital.entity.dto;

import lombok.Data;

/**
 * @author kirit
 * @version 1.0
 * @description: TODO
 * @date 2023/7/3 8:51
 */
@Data
public class DoctorSearchDto {
    private String name;

    private Integer pageNum;

    private Integer pageSize;

    private Integer id;

    private String idNum;

    private String phone;

    private Double fee;

    private Integer departId;

    private Integer role;

}
