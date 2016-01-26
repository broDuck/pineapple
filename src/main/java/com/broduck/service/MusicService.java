package com.broduck.service;

import com.broduck.domain.Criteria;
import com.broduck.domain.MusicVO;
import com.broduck.domain.SearchCriteria;

import java.util.List;

/**
 * Created by broDuck on 2016-01-26.
 */
public interface MusicService {
    public void create(MusicVO music) throws Exception;

    public MusicVO read(Integer mno) throws Exception;

    public List<MusicVO> readAll() throws Exception;

    public List<MusicVO> listPage(SearchCriteria cri) throws Exception;

    public int listSearchCount(SearchCriteria cri) throws Exception;
}
