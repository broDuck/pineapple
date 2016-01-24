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

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

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
    public void loginPOST(LoginDTO dto, HttpSession session, Model model) throws Exception {
        UserVO vo = service.login(dto);

        if (vo == null) {
            return;
        }

        model.addAttribute("userVO", vo);
    }
}
