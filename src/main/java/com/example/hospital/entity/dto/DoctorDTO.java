package com.example.hospital.entity.dto;


import lombok.Data;

import java.util.Date;

@Data
public class DoctorDTO {

    private Integer id;

    private String name;

    private Integer sex;

    private Date birthday;

    private String idNum;

    private String phone;

    private Double fee;

    private String introduce;

    private Integer departId;

    private Integer role;
}
