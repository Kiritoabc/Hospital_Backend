package com.example.hospital.entity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.example.hospital.entity.dto.RegisterDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @TableName doctor
 */
@TableName(value ="doctor")
@Data
@NoArgsConstructor
public class Doctor implements Serializable {
    /**
     * 唯一识别ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 医生姓名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 性别 0:女，1:男，2：其他
     */
    @TableField(value = "sex")
    private Integer sex;

    /**
     * 出生日期
     */
    @TableField(value = "birthday")
    private Date birthday;

    /**
     * 身份证号
     */
    @TableField(value = "id_num")
    private String idNum;

    /**
     * 手机号码
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 挂号费
     */
    @TableField(value = "fee")
    private Double fee;

    /**
     * 基本介绍
     */
    @TableField(value = "introduce")
    private String introduce;

    /**
     * 科室ID
     */
    @TableField(value = "depart_id")
    private Integer departId;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 生成的ID
     */
    @TableField(value = "uuid")
    private String uuid;

    /**
     * 角色 0：医生，1：管理员，2：医生加管理员
     */
    @TableField(value = "role")
    private Integer role;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Doctor(RegisterDTO dto){
        this.name = dto.getName();
        this.sex = dto.getSex();
        this.birthday = dto.getBirthday();
        this.idNum = dto.getIdNum();
        this.phone = dto.getPhone();
        this.fee = dto.getFee();
        this.introduce = dto.getIntroduce();
        this.departId = dto.getDepartId();
    }
}