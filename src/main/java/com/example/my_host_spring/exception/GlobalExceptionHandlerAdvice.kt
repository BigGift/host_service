package com.example.my_host_spring.exception

import com.example.my_host_spring.pojo.ResponseMessage
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandlerAdvice {

    var log: Logger = LoggerFactory.getLogger(GlobalExceptionHandlerAdvice::class.java)

    @ExceptionHandler(Exception::class) //处理哪个异常
    fun handlerException(e:Exception,
                         httpServletRequest: HttpServletRequest):ResponseMessage{
        log.error("error",e)
        return ResponseMessage(
                500,
                "error",
                null)
    }

}