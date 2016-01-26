package com.broduck.persistence;

import com.broduck.domain.MusicVO;
import com.broduck.domain.SearchCriteria;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by broDuck on 2016-01-25.
 */

@Repository
public class MusicDAOImpl implements MusicDAO {

    @Inject
    private SqlSession session;

    private static String namespace = "com.broduck.mapper.MusicMapper";

    @Override
    public void create(MusicVO vo) throws Exception {
        session.insert(namespace + ".create", vo);
    }

    @Override
    public MusicVO read(Integer mno) throws Exception {
        return session.selectOne(namespace + ".read", mno);
    }

    @Override
    public List<MusicVO> listPage(SearchCriteria cri) throws Exception {

        return session.selectList(namespace + ".listPage", cri);
    }
    @Override
    public int listSearchCount(SearchCriteria cri) throws Exception {

        return session.selectOne(namespace + ".listSearchCount", cri);
    }
}
