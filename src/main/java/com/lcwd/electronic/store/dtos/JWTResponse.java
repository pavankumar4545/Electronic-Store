package com.lcwd.electronic.store.dtos;


import com.lcwd.electronic.store.entities.RefereshToken;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JWTResponse {

    private String token;

    private UserDtos userDtos;

//    private String refersehToken;

    private RefereshTokenDto refereshTokenDto;

}
