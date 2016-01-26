package com.broduck.service;

import com.broduck.domain.UserVO;
import com.broduck.dto.LoginDTO;
import com.broduck.persistence.UserDAO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;

/**
 * Created by broduck on 16. 1. 24.
 */

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDAO dao;

    @Override
    public UserVO login(LoginDTO dto) throws Exception {
        return dao.login(dto);
    }

    @Override
    public void keepLogin(String id, String sessionId, Date next) throws Exception {
        dao.keepLogin(id, sessionId, next);
    }

    @Override
    public UserVO checkLoginBefore(String value) throws Exception{
        return dao.checkUserWithSessionKey(value);
    }

    @Override
    public int checkUser(LoginDTO dto) throws Exception {
        return dao.checkUser(dto);
    }
}
