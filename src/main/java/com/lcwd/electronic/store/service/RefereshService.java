package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dtos.RefereshTokenDto;
import com.lcwd.electronic.store.entities.RefereshToken;

public interface RefereshService {


    public RefereshTokenDto createRefreshToken(String userName);

    public RefereshTokenDto findByToken(String token);

    public RefereshTokenDto verifyRefreshToken(RefereshTokenDto tokenDto);

}
