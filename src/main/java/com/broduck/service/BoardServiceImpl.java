package com.broduck.service;

import java.util.List;

import javax.inject.Inject;

import com.broduck.domain.AttachVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.broduck.domain.BoardVO;
import com.broduck.domain.Criteria;
import com.broduck.domain.SearchCriteria;
import com.broduck.persistence.BoardDAO;

@Service
public class BoardServiceImpl implements BoardService {

  @Inject
  private BoardDAO dao;

  @Transactional
  @Override
  public void regist(BoardVO board) throws Exception {
    dao.create(board);
    AttachVO attach = new AttachVO();
    Integer bno = dao.getBno(board.getWriter());
    attach.setBno(bno);

    String[] files = board.getFiles();

    if (files == null) {
      return;
    }

    for (String fileName : files) {
      attach.setFullName(fileName);
      dao.addAttach(attach);
    }
  }

  @Transactional(isolation=Isolation.READ_COMMITTED)
  @Override
  public BoardVO read(Integer bno) throws Exception {
    dao.updateViewCnt(bno);
    return dao.read(bno);
  }

  @Override
  public void modify(BoardVO board) throws Exception {
    dao.update(board);
  }

  @Transactional
  @Override
  public void remove(Integer bno) throws Exception {
    dao.deleteAttach(bno);
    dao.delete(bno);
  }

  @Override
  public List<BoardVO> listAll() throws Exception {
    return dao.listAll();
  }

  @Override
  public List<BoardVO> listCriteria(Criteria cri) throws Exception {

    return dao.listCriteria(cri);
  }

  @Override
  public int listCountCriteria(Criteria cri) throws Exception {

    return dao.countPaging(cri);
  }

  @Override
  public List<BoardVO> listSearchCriteria(SearchCriteria cri) throws Exception {

    return dao.listSearch(cri);
  }

  @Override
  public int listSearchCount(SearchCriteria cri) throws Exception {

    return dao.listSearchCount(cri);
  }

  @Override
  public List<String> getAttach(Integer bno) throws Exception {
    return dao.getAttach(bno);
  }

}
