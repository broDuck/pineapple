package com.broduck.service;

import com.broduck.domain.UserVO;
import com.broduck.dto.LoginDTO;

/**
 * Created by broduck on 16. 1. 24.
 */
public interface UserService {
    public UserVO login(LoginDTO dto) throws Exception;
}
