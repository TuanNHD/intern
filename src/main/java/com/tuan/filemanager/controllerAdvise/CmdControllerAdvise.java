package com.tuan.filemanager.controllerAdvise;

import com.tuan.filemanager.exception.CustomException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CmdControllerAdvise {
    @ExceptionHandler
    public String handleException(CustomException e) {
        return "redirect:/control?error="+e.getMessage();
    }

}
