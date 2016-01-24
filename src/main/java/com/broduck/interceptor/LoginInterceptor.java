package com.broduck.interceptor;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by broduck on 16. 1. 24.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static final String LOGIN = "login";

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {

        HttpSession session = request.getSession();
        ModelMap modelMap = modelAndView.getModelMap();
        Object userVO = modelMap.get("userVO");

        if (userVO != null) {
            System.out.println("INFO : new login success");
            session.setAttribute(LOGIN, userVO);
            response.sendRedirect("/");
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        HttpSession session = request.getSession();

        if (session.getAttribute(LOGIN) != null) {
            System.out.println("INFO : clear login data before");
            session.removeAttribute(LOGIN);
        }

        return true;
    }
}