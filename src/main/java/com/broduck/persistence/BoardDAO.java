package com.broduck.persistence;

import java.util.List;

import com.broduck.domain.AttachVO;
import com.broduck.domain.BoardVO;
import com.broduck.domain.Criteria;
import com.broduck.domain.SearchCriteria;

public interface BoardDAO {

  public Integer create(BoardVO vo) throws Exception;

  public BoardVO read(Integer bno) throws Exception;

  public void update(BoardVO vo) throws Exception;

  public void delete(Integer bno) throws Exception;

  public List<BoardVO> listAll() throws Exception;

  public List<BoardVO> listPage(int page) throws Exception;

  public List<BoardVO> listCriteria(Criteria cri) throws Exception;

  public int countPaging(Criteria cri) throws Exception;
  
  //use for dynamic sql
  
  public List<BoardVO> listSearch(SearchCriteria cri)throws Exception;
  
  public int listSearchCount(SearchCriteria cri)throws Exception;

  public void updateReplyCnt(Integer bno, int amount)throws Exception;

  public void updateViewCnt(Integer bno)throws Exception;

  public void addAttach(AttachVO vo) throws Exception;

  public List<String> getAttach(Integer bno) throws Exception;

  public Integer getBno(String writer) throws Exception;

  public void deleteAttach(Integer bno) throws Exception;

  public void replaceAttach(AttachVO vo) throws Exception;


}
