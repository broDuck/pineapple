package com.broduck.service;

import com.broduck.domain.UserVO;
import com.broduck.dto.LoginDTO;
import com.broduck.persistence.UserDAO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

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
}
