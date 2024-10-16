package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.entities.User;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.Instant;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefereshTokenDto {
    private int id;
    private String token;
    private Instant expiryDate;
    @OneToOne
    private UserDtos user;
}
