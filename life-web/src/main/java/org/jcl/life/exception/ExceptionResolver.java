package org.jcl.life.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 */
@Component
public class ExceptionResolver implements HandlerExceptionResolver {
    private static Logger logger =
            LoggerFactory.getLogger(ExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Object handler, Exception e) {
        HandlerMethod handlerTmp = (HandlerMethod) handler;
        logger.info("发生异常: [{}]", request.getAttribute("javax.servlet.error" +
                ".exception_type"));
        return null;
    }
}
