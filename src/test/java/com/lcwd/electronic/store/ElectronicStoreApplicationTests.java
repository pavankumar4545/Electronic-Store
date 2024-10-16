package com.lcwd.electronic.store;

import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.repositries.UserReposetries;
import com.lcwd.electronic.store.security.JWTHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ElectronicStoreApplicationTests {

	@Autowired
	private UserReposetries userReposetries;
	@Autowired
	private JWTHelper jwtHelper;

	@Test
	void contextLoads() {
	}

	@Test
	 void testToken(){
		User user = userReposetries.findByEmail("pavankumarmadli48@gmail.com").get();
		String s = jwtHelper.generateToken(user);


		System.out.println(s);
	 }

}
