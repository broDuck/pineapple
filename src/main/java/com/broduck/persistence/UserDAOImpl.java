package com.broduck.persistence;

import com.broduck.domain.UserVO;
import com.broduck.dto.LoginDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.jws.soap.SOAPBinding;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    @Override
    public void keepLogin(String id, String sessionId, Date next) throws Exception {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        paramMap.put("sessionId", sessionId);
        paramMap.put("next", next);

        session.update(namespace + ".keepLogin", paramMap);
    }

    @Override
    public UserVO checkUserWithSessionKey(String value) throws Exception {
        return session.selectOne(namespace + ".checkUserWithSessionKey", value);
    }
}
