package com.broduck.service;

import com.broduck.domain.UserVO;
import com.broduck.dto.LoginDTO;

import java.util.Date;

/**
 * Created by broduck on 16. 1. 24.
 */
public interface UserService {
    public UserVO login(LoginDTO dto) throws Exception;

    public void keepLogin(String id, String sessionId, Date next) throws Exception;

    public UserVO checkLoginBefore(String value) throws Exception;

    public int checkUser(LoginDTO dto) throws Exception;
}
