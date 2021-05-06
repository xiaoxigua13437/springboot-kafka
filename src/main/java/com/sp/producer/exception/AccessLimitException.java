package com.sp.producer.exception;


/**
 *
 * 自定义异常类
 *
 * @author yushu.zhao
 * @create 2020-12-17 09:47
 */
public class AccessLimitException extends Exception{

    private static final long serialVersionUID = 1555967171104727461L;

    public AccessLimitException(){
        super("HTTP请求超出设定的限制");
    }

    public AccessLimitException(String message){
        super(message);
    }
}
