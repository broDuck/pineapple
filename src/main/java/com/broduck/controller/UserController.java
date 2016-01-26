package com.broduck.controller;

import com.broduck.domain.PageMaker;
import com.broduck.domain.UserVO;
import com.broduck.dto.LoginDTO;
import com.broduck.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by broduck on 16. 1. 24.
 */

@Controller
@RequestMapping("/user")
public class UserController {

    @Inject
    private UserService service;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void loginGET(@ModelAttribute("dto")LoginDTO dto) {

    }

    @RequestMapping(value = "/loginPost", method = RequestMethod.POST)
    public String loginPOST(LoginDTO dto, HttpSession session, Model model) throws Exception {
        UserVO vo = service.login(dto);

        if (vo == null) {
            return "redirect:/";
        }

        model.addAttribute("userVO", vo);

        if (dto.isUseCookie()) {
            int amount = 60 * 60 * 24 * 7;

            Date sessionLimit = new Date(System.currentTimeMillis() + (1000 * amount));

            service.keepLogin(vo.getId(), session.getId(), sessionLimit);
        }

        return "redirect:/music/list";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

        Object obj = session.getAttribute("login");

        if (obj != null) {
            UserVO vo = (UserVO) obj;

            session.removeAttribute("login");
            session.invalidate();

            Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");

            if (loginCookie != null) {
                loginCookie.setPath("/");
                loginCookie.setMaxAge(0);
                response.addCookie(loginCookie);
                service.keepLogin(vo.getId(), session.getId(), new Date());
            }
        }

        return "redirect:/";
    }
}
