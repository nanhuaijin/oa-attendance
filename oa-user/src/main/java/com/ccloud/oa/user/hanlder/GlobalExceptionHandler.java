package com.ccloud.oa.user.hanlder;

import com.ccloud.oa.common.exception.ApplicationException;
import com.ccloud.oa.common.result.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : breeze
 * @date : 2020/8/14
 * @description : 全局异常处理
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseResponse error(Exception e){
        e.printStackTrace();
        return BaseResponse.error();
    }

    @ExceptionHandler(ApplicationException.class)
    @ResponseBody
    public BaseResponse error(ApplicationException e){
        log.error(ExceptionUtils.getMessage(e));
        return BaseResponse.error().message(e.getMessage()).code(e.getCode());
    }
}
