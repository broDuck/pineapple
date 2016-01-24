package com.broduck.persistence;

import com.broduck.domain.UserVO;
import com.broduck.dto.LoginDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

/**
 * Created by broduck on 16. 1. 24.
 */

@Repository
public class UserDAOImpl implements UserDAO {
    @Inject
    private SqlSession session;

    private static String namespace = "com.broduck.mapper.UserMapper";

    @Override
    public UserVO login(LoginDTO dto) throws Exception {
        return session.selectOne(namespace + ".login", dto);
    }
}
