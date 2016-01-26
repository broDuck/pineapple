package com.broduck.controller;

import com.broduck.domain.PageMaker;
import com.broduck.domain.SearchCriteria;
import com.broduck.service.MusicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.inject.Inject;

/**
 * Created by broDuck on 2016-01-26.
 */

@Controller
@RequestMapping("/music/*")
public class MusicController {

    @Resource
    private String musicPath;

    private static final Logger logger = LoggerFactory.getLogger(MusicController.class);

    @Inject
    private MusicService service;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public void registGET() throws Exception {

    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public void listPage(@ModelAttribute("cri")SearchCriteria cri, Model model) throws Exception {
        logger.info(cri.toString());

        model.addAttribute("list", service.listPage(cri));

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);

        pageMaker.setTotalCount(service.listSearchCount(cri));

        model.addAttribute("pageMaker", pageMaker);
    }

    @RequestMapping(value = "/player", method = RequestMethod.GET)
    public void playMusicGET(@RequestParam("mno") int mno, Model model) throws Exception {
        if (mno == 0) {
            model.addAttribute("list", service.readAll());
        } else {
            model.addAttribute(service.read(mno));
        }
    }


}
