package com.broduck.service;

import com.broduck.domain.Criteria;
import com.broduck.domain.MusicVO;
import com.broduck.domain.SearchCriteria;
import com.broduck.persistence.MusicDAO;
import org.springframework.stereotype.Service;

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

    @Override
    public MusicVO read(Integer mno) throws Exception {
        return dao.read(mno);
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
