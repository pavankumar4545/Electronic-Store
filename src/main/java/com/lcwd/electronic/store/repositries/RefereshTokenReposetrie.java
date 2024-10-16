package com.lcwd.electronic.store.repositries;

import com.lcwd.electronic.store.entities.RefereshToken;
import com.lcwd.electronic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefereshTokenReposetrie extends JpaRepository<RefereshToken,Integer> {

    Optional<RefereshToken> findByToken(String token);

    Optional<RefereshToken> findByUser(User user);


}
