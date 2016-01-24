package com.broduck.persistence;

import com.broduck.domain.UserVO;
import com.broduck.dto.LoginDTO;

/**
 * Created by broduck on 16. 1. 24.
 */
public interface UserDAO {
    public UserVO login(LoginDTO dto) throws Exception;
}
