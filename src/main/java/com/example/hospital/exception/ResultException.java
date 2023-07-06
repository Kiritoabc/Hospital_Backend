package com.example.hospital.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * 用来处理在shiro的realm中无法捕获到自定义异常的问题
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultException {
    private String code;
    private String message;

    public static String getResult(String code, String message) throws JsonProcessingException {
        HashMap<String, String> resultMap = new HashMap<>();
        resultMap.put("code", code);
        resultMap.put("message", message);
        resultMap.put("timeStamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        String resultStr = new ObjectMapper().writeValueAsString(resultMap);
        return resultStr;
    }

}
