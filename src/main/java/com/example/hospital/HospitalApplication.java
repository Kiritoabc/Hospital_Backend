package com.example.hospital;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author kirito
 * @version 1.0
 * @description: TODO
 * @date 2023/7/1 9:59
 */

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.example.hospital.mapper")
public class HospitalApplication {
    public static void main(String[] args) {
        SpringApplication.run(HospitalApplication.class,args);
    }
}
