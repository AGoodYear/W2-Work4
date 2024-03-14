package com.ivmiku.W4R3.controller;

import com.chenkaiwei.krest.exceptions.KrestErrorController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 错误返回类
 * @author Aurora
 */
@Slf4j
@Controller
public class ErrorController extends KrestErrorController {

    @Override
    protected Map<String, Object> getErrorResponseBody(HttpServletRequest request, Map<String, Object> errorAttributes, Throwable exception) {
        return super.getErrorResponseBody(request, errorAttributes, exception);
    }
}
