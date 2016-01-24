package com.broduck.persistence;

import com.broduck.domain.UserVO;
import com.broduck.dto.LoginDTO;

import java.util.Date;

/**
 * Created by broduck on 16. 1. 24.
 */
public interface UserDAO {
    public UserVO login(LoginDTO dto) throws Exception;

    public void keepLogin(String id, String sessionId, Date next) throws Exception;

    public UserVO checkUserWithSessionKey(String value) throws Exception;
}
