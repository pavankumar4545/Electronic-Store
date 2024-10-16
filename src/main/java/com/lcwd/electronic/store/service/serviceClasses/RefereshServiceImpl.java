package com.lcwd.electronic.store.service.serviceClasses;

import com.lcwd.electronic.store.dtos.RefereshTokenDto;
import com.lcwd.electronic.store.entities.RefereshToken;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.repositries.RefereshTokenReposetrie;
import com.lcwd.electronic.store.repositries.UserReposetries;
import com.lcwd.electronic.store.service.RefereshService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefereshServiceImpl implements RefereshService {
   @Autowired
    private UserReposetries userReposetries;
   @Autowired
   private RefereshTokenReposetrie refereshTokenReposetrie;
   @Autowired
   private ModelMapper modelMapper;

    @Override
    public RefereshTokenDto createRefreshToken(String userName) {
        User user = userReposetries.findByEmail(userName).orElseThrow(() -> new UsernameNotFoundException("User Name Not Present"));

        RefereshToken refereshToken = refereshTokenReposetrie.findByUser(user).orElse(null);
        if(refereshToken==null) {
            refereshToken = RefereshToken.builder()
                    .user(user)
                    .token(UUID.randomUUID().toString())
                    .expiryDate(Instant.now().plusSeconds(5 * 24 * 60 * 60))

                    .build();
        }else {
            refereshToken.setToken(UUID.randomUUID().toString());
            refereshToken.setExpiryDate(Instant.now().plusSeconds(5 * 24 * 60 * 60));
        }
        RefereshToken save = refereshTokenReposetrie.save(refereshToken);

        return  modelMapper.map(save, RefereshTokenDto.class);
    }

    @Override
    public RefereshTokenDto findByToken(String token) {
        RefereshToken refereshToken = refereshTokenReposetrie.findByToken(token).orElseThrow(() -> new UsernameNotFoundException("Provided Token Is Not Present"));


        return modelMapper.map(refereshToken,RefereshTokenDto.class);
    }

    @Override
    public RefereshTokenDto verifyRefreshToken(RefereshTokenDto tokenDto) {

        RefereshToken refereshToken = modelMapper.map(tokenDto, RefereshToken.class);
        if(tokenDto.getExpiryDate().compareTo(Instant.now())<0){
            refereshTokenReposetrie.delete(refereshToken);
            throw new RuntimeException("Referseh Token Expired");
        }


        return tokenDto;
    }


}
