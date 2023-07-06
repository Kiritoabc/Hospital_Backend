package com.example.hospital.entity.dto;

import lombok.Data;

import java.util.Date;

@Data
public class RegisterDTO {

    /**
     * 医生姓名
     */
    private String name;
    /**
     * 性别 0:女，1:男，2：其他
     */
    private Integer sex;
    /**
     * 出生日期
     */
    private Date birthday;
    /**
     * 身份证号
     */
    private String idNum;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 挂号费
     */
    private double fee;
    /**
     * 基本介绍
     */
    private String introduce;
    /**
     * 科室ID
     */
    private Integer departId;
    /**
     * 登陆密码
     */
    private String password;
}
