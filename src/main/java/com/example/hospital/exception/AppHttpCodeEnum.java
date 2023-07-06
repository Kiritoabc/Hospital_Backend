package com.example.hospital.exception;
/**
* @author 牵风散步的雲
* @Description //
 *                          更加细节的HttpCode返回
* @Date 2023-3-22 12:47
* @param  * @param null
* @return
**/

public enum AppHttpCodeEnum {

    SUCCESS(200, "操作成功"),
    NEED_LOGIN(401, "需要登陆后操作"),
    UPLOAD_OK(201, "文献上传成功"),
    AUTHOR_SAVE_OK(202,"作者保存成功"),
    AuTHOR_SAVE_ERROR(402,"作者保存失败"),
    ZINC_SEARCH_OK(203,"zincSearch搜索成功"),
    DETAILS_SEARCH_OK(204,"文献详情查询成功"),
    DETAILS_SEARCH_RESULT_NULL(403,"该文献不存在或该页不存在"),
    DETAILS_SEARC_NAME_IS_NULL(405,"查询出错，查询文献名称给不能为空"),
    DOCUMENT_UPDATE_OK(205,"文献更新成功"),
    LOG_SEARCH_OK(206,"日志查询成功"),
    LOG_SEARCH_ERROR(406,"查询记录的用户名或文献名不能为空"),
    USER_REGISTER_OK(207,"用户注册成功"),
    USER_REGISTER_ERROR(407,"用户注册失败"),
    USER_NAME_IS_NULL(408,"用户名不能为空"),
    USER_LOGIN_OK(208,"用户登录成功"),
    USER_QUIT_OK(209,"用户登出成功"),

    PASSWORD_UPDATE_OK(210,"密码修改成功"),
    USER_LOGIN_PASSWORD_ERROR(409,"用户登录密码错误"),
    USER_UPDATE_PASSWORD_ERROR(410,"两次密码不一致"),
    USER_UPDATE_OK(209,"用户更新成功"),
    ERROR(400,"操作失败"),
    USER_NAME_IS_OCCUPIED(411,"用户名已被占用"),
    EMAIL_IS_OCCUPIED(412,"邮箱已被占用"),
    PHONE_IS_OCCUPIED(413,"手机号已被占用"),
    INVALID_EMAIL(400,"邮箱无效"),
    INVALID_Token(400,"token无效"),
    ERROR_VERITY_CODE(400,"验证码错误"),
    USER_EMAIL_ERROR(400,"邮箱错误");

    final Integer code;
    final String msg;

    AppHttpCodeEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg(){
        return msg;
    }
}
