package com.example.hospital.entity.dto;

import lombok.Data;

/**
 * @author kirit
 * @version 1.0
 * @description: TODO
 * @date 2023/7/4 16:59
 */
@Data
public class RolesValue {
    private long doctor;

    private long admin;

    private long doctorAndAdmin;
}
