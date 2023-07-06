package com.example.hospital.entity.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @TableName call
 */
@TableName(value ="call")
@Data
public class Call implements Serializable {
    /**
     * 出诊ID
     */
    @TableId(value = "call_id")
    private Integer callId;

    /**
     * 医生ID
     */
    @TableField(value = "doctor_id")
    private Integer doctorId;

    /**
     * 出诊日期
     */
    @TableField(value = "call_date")
    private Date callDate;

    /**
     * 开始时间
     */
    @TableField(value = "from_time")
    private Date fromTime;

    /**
     * 结束时间
     */
    @TableField(value = "to_time")
    private Date toTime;

    /**
     * 医生姓名
     */
    @TableField(value = "doctor_name")
    private String doctorName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}