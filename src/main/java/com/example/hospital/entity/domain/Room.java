package com.example.hospital.entity.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
/**
 *
 * @TableName room
 */
@TableName(value ="room")
@Data
public class Room implements Serializable {
    /**
     * 诊室ID
     */
    @TableId(value = "room_id", type = IdType.AUTO)
    private Integer roomId;

    /**
     * 诊室名称
     */
    @TableField(value = "room_name")
    private String roomName;

    /**
     * 诊室介绍
     */
    @TableField(value = "room_introduce")
    private String roomIntroduce;

    /**
     * 隶属科室ID
     */
    @TableField(value = "depart_id")
    private Integer departId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}