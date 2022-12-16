package com.example.kursach;

import com.example.kursach.Controllers.HomeController;
import com.example.kursach.Models.Role;
import com.example.kursach.Models.User;
import com.example.kursach.Repositories.UserRepositorie;
import groovyjarjarpicocli.CommandLine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Date;
@SpringBootTest
class KursachApplicationTests {
	@Autowired
	UserRepositorie userRepositorie;
	Model modal;

	@Test
	void T_Registartion() {
	long id= 100L;
		User us = new User();
		us.setActive(true);
		us.setPassword("Qwerty1234");
		us.setPhone("+8(900)345-51-67");
		us.setUsername("Qwerty1234");
		us.setBirthday(Date.valueOf("2000-12-12"));
		us.setName("Владимер");
		us.setNumber_passport(123456);
		us.setSurname("Ларионов");
		us.setMiddleName("Владимерович");
		userRepositorie.save(us);
		User user=userRepositorie.findByFIO("Ларионов", "Владимер", "Владимерович");
		assertEquals(us.getFIO(),user.getFIO());
//		HomeController hc = new HomeController();
//		hc.reg1(us,modal);
	}

}
