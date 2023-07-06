package com.example.hospital.entity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
/**
 *
 * @TableName depart
 */
@TableName(value ="depart")
@Data
public class Depart implements Serializable {
    /**
     * 科室ID
     */
    @TableId(value = "depart_id", type = IdType.AUTO)
    private Integer departId;

    /**
     * 科室名称
     */
    @TableField(value = "depart_name")
    private String departName;

    /**
     * 科室介绍
     */
    @TableField(value = "depart_introduce")
    private String departIntroduce;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}