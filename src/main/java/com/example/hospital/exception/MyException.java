package com.example.hospital.exception;

import lombok.Data;

/**
 * 自定义异常类：包含http状态码,提示信息（父类中RuntimeException已经有message属性）
 */
@Data
public class MyException extends RuntimeException{

    //提供http状态码属性，动态设置
    private int status;

    //ExceptionEnums中包含 装填码 以及提示消息
    //public MyException(ExceptionEnums enums) {
    //    this.status = enums.getStatus();
    //}

    public MyException(AppHttpCodeEnum enums) {
        super(enums.getMsg());
        this.status = enums.getCode();
    }
}