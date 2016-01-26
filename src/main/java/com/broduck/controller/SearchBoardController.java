package com.broduck.controller;

import com.broduck.domain.BoardVO;
import com.broduck.domain.PageMaker;
import com.broduck.domain.SearchCriteria;
import com.broduck.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import java.util.List;

@Controller
@RequestMapping("/sboard/*")
public class SearchBoardController {

  private static final Logger logger = LoggerFactory.getLogger(SearchBoardController.class);

  @Inject
  private BoardService service;

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public void listPage(@ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {

    logger.info(cri.toString());

    model.addAttribute("list", service.listSearchCriteria(cri));

    PageMaker pageMaker = new PageMaker();
    pageMaker.setCri(cri);

    pageMaker.setTotalCount(service.listSearchCount(cri));

    model.addAttribute("pageMaker", pageMaker);
  }

  @RequestMapping(value = "/readPage", method = RequestMethod.GET)
  public void read(@RequestParam("bno") int bno, @ModelAttribute("cri") SearchCriteria cri, Model model)
      throws Exception {

    model.addAttribute(service.read(bno));
  }

  @RequestMapping(value = "/removePage", method = RequestMethod.POST)
  public String remove(@RequestParam("bno") int bno, SearchCriteria cri, RedirectAttributes rttr) throws Exception {

    service.remove(bno);

    rttr.addAttribute("page", cri.getPage());
    rttr.addAttribute("perPageNum", cri.getPerPageNum());
    rttr.addAttribute("searchType", cri.getSearchType());
    rttr.addAttribute("keyword", cri.getKeyword());

    rttr.addFlashAttribute("msg", "SUCCESS");

    return "redirect:/sboard/list";
  }

  @RequestMapping(value = "/modifyPage", method = RequestMethod.GET)
  public void modifyPagingGET(int bno, @ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {

    model.addAttribute(service.read(bno));
  }

  @RequestMapping(value = "/modifyPage", method = RequestMethod.POST)
  public String modifyPagingPOST(BoardVO board, SearchCriteria cri, RedirectAttributes rttr) throws Exception {

    logger.info(cri.toString());
    service.modify(board);

    rttr.addAttribute("page", cri.getPage());
    rttr.addAttribute("perPageNum", cri.getPerPageNum());
    rttr.addAttribute("searchType", cri.getSearchType());
    rttr.addAttribute("keyword", cri.getKeyword());

    rttr.addFlashAttribute("msg", "SUCCESS");

    logger.info(rttr.toString());

    return "redirect:/sboard/list";
  }

  @RequestMapping(value = "/register", method = RequestMethod.GET)
  public void registGET() throws Exception {

  }

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public String registPOST(BoardVO board, RedirectAttributes rttr) throws Exception {

    service.regist(board);

    rttr.addFlashAttribute("msg", "SUCCESS");

    return "redirect:/sboard/list";
  }

  @RequestMapping("/getAttach/{bno}")
  @ResponseBody
  public List<String> getAttach(@PathVariable("bno") Integer bno) throws Exception {
    return service.getAttach(bno);
  }
}
