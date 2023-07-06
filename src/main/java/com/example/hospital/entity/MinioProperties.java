package com.example.hospital.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author kirit
 * @version 1.0
 * @description: TODO
 * @date 2023/7/1 10:11
 */
@ConfigurationProperties(prefix = "minio")
@Component
@Data
public class MinioProperties {

    /**
     * 连接地址
     */
    private String endpoint;

    /**
     *  用户名
     */
    private String accessKey;

    /**
     *  密码
     */
    private String secretKey;
}

