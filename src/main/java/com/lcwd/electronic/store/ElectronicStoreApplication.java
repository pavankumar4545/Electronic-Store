package com.lcwd.electronic.store;

import com.lcwd.electronic.store.entities.Roles;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.repositries.RolesReposetries;
import com.lcwd.electronic.store.repositries.UserReposetries;
import com.lcwd.electronic.store.security.JWTHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@EnableWebMvc
public class ElectronicStoreApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ElectronicStoreApplication.class, args);
	}


	@Autowired
	private RolesReposetries rolesReposetries;
	@Autowired
	private UserReposetries userReposetries;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JWTHelper jwtHelper;

	@Override
	public void run(String... args) throws Exception {

//		Roles roles1 = rolesReposetries.findByRoleName("ROLE_ADMIN").orElse(null);
//		if(roles1==null){
//			 roles1=new Roles();
//			roles1.setRoleId(UUID.randomUUID().toString());
//			roles1.setRoleName("ROLE_ADMIN");
//			rolesReposetries.save(roles1);
//
//		}
//
//		Roles roles2=rolesReposetries.findByRoleName("ROLE_NORMAL").orElse(null);
//		if(roles2==null) {
//			 roles2 = new Roles();
//			roles2.setRoleId(UUID.randomUUID().toString());
//			roles2.setRoleName("ROLE_NORMAL");
//			rolesReposetries.save(roles2);
//		}
//
//		User user = userReposetries.findByEmail("pavankumarmadli48@gmail.com").orElse(null);
//
//		if(user==null){
//			user=new User();
//			user.setName("Pavankumar");
//			user.setEmail("pavankumarmadli48@gmail.com");
//			user.setPassword(passwordEncoder.encode("pavan123"));
//			user.setRoles(List.of(roles1));
//			user.setUserId(UUID.randomUUID().toString());
//
//			userReposetries.save(user);
//		}

		User user = userReposetries.findByEmail("pavankumarmadli48@gmail.com").get();
		String token = jwtHelper.generateToken(user);


		System.out.println(token);

		String userNameFromTokens = jwtHelper.getUserNameFromTokens(token);
		System.out.println(userNameFromTokens);

		Date expirationDateFromToken = jwtHelper.getExpirationDateFromToken(token);
		System.out.println(expirationDateFromToken);

	}





}
