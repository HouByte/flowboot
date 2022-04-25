package cn.flowboot.core.aspect;


import cn.flowboot.common.croe.domain.AjaxResult;
import cn.flowboot.common.exception.ParamsException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Vincent Vic
 * @Date 2020/12/6 18:16
 * @Version 1.0
 * @Description 统一异常处理
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {
    @ResponseBody
    //500 INTERNAL_SERVER_ERROR 服务器错误
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public AjaxResult handlerException(HttpServletRequest req,
                                               Exception ex) {
        log.error("{} 捕获到异常 ==> {}",req,ex);
        AjaxResult ajaxResult = AjaxResult.error("系统出现故障，请联系管理员");
        return ajaxResult;
    }


    @ResponseBody
    //412 PRECONDITION_FAILED 客户端请求信息的先决条件错误
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ExceptionHandler(value = BindException.class)
    public AjaxResult handlerBindException(HttpServletRequest req,
                                                     BindException ex) {
        log.error("{}  参数校验异常 ==> {}",req,ex);
        AjaxResult response = AjaxResult.error(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return response;
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = JwtException.class)
    public AjaxResult handlerJwtException(HttpServletRequest req,
                                          JwtException ex) {
        log.error("{}  jwt token 异常 ==> {}",req,ex);
        AjaxResult response = AjaxResult.error(ex.getMessage());
        return response;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    @ExceptionHandler(value = ParamsException.class)
    public AjaxResult handlerJwtException(HttpServletRequest req,
                                          ParamsException ex) {
        log.error("{}  参数 异常 ==> {}",req,ex);
        AjaxResult response = AjaxResult.error(ex.getMessage());
        return response;
    }

}
