package com.broduck.service;

import com.broduck.domain.Criteria;
import com.broduck.domain.MusicVO;
import com.broduck.domain.SearchCriteria;
import com.broduck.persistence.MusicDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by broDuck on 2016-01-26.
 */

@Service
public class MusicServiceImpl implements MusicService {

    @Inject
    private MusicDAO dao;

    @Override
    public void create(MusicVO music) throws Exception {
        dao.create(music);
    }

    @Transactional
    @Override
    public MusicVO read(Integer mno) throws Exception {
        dao.viewCnt(mno);
        return dao.read(mno);
    }

    @Override
    public List<MusicVO> readAll() throws Exception {
        return dao.readAll();
    }

    @Override
    public List<MusicVO> listPage(SearchCriteria cri) throws Exception {
        return dao.listPage(cri);
    }

    @Override
    public int listSearchCount(SearchCriteria cri) throws Exception {

        return dao.listSearchCount(cri);
    }
}
