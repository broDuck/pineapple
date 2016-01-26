package com.broduck.persistence;

import com.broduck.domain.MusicVO;
import com.broduck.domain.SearchCriteria;

import java.util.List;

/**
 * Created by broDuck on 2016-01-25.
 */

public interface MusicDAO {
    public void create(MusicVO vo) throws Exception;

    public MusicVO read(Integer mno) throws Exception;

    public List<MusicVO> readAll() throws Exception;

    public List<MusicVO> listPage(SearchCriteria cri) throws Exception;

    public int listSearchCount(SearchCriteria cri)throws Exception;

    public void viewCnt(Integer mno) throws Exception;
}
