package com.leyunone.laboratory.web.project.resultcode.controller;

import com.leyunone.laboratory.web.project.resultcode.bean.DataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@ControllerAdvice(annotations = {RestController.class})
@ResponseBody
public class RestExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);


    @ExceptionHandler(Exception.class)
    public DataResponse handleValidationException(Exception e){
        logger.error(e.getMessage());
        e.printStackTrace();
        return DataResponse.buildFailure(e.getMessage());
    }

}
