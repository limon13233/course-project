package com.example.kursach;

import com.example.kursach.Controllers.Api;
import com.example.kursach.Models.Provider;
import com.example.kursach.Models.User;
import com.example.kursach.Repositories.ProviderRepositorie;
import com.example.kursach.Repositories.UserRepositorie;
import com.fasterxml.jackson.databind.ObjectMapper;

import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.sql.Date;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@WebMvcTest(Api.class)
@RunWith(SpringRunner.class)
class KursachApplicationTests {
	@MockBean
	UserRepositorie userRepositorie;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private DataSource dataSource;
	@MockBean
	ProviderRepositorie providerRepositorie;

	@Test
	void T_Registartion() throws Exception {
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
		Mockito.when(userRepositorie.save(us)).thenReturn(Optional.of(us).orElseThrow());


		mockMvc.perform(post("/api/users")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.ALL)
				.content(objectMapper.writeValueAsString(us)))
				.andExpect(status().isOk());
	}
	@Test
	void T_RegistartionFailed() throws Exception {
		User us = new User();
		us.setActive(true);
		us.setPassword("Qwerty1234");
		us.setPhone("");
		us.setUsername("");
		us.setBirthday(Date.valueOf("2000-12-12"));
		us.setName("Владимер");
		us.setNumber_passport(123456);
		us.setSurname("Ларионов");
		us.setMiddleName("Владимерович");
		Mockito.when(userRepositorie.save(us)).thenReturn(Optional.of(us).orElseThrow());


		mockMvc.perform(post("/api/users")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.ALL)
				.content(objectMapper.writeValueAsString(us)))
				.andExpect(status().isOk());
	}
	@Test()
	void T_Dellite() {
		long id = 18L;
		willDoNothing().given(userRepositorie).deleteById(id);
		try {
			mockMvc.perform(delete("/api/users/del/"+id)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	@Test
	public void givenEmployees_whenGetEmployees_thenStatus200()
			throws Exception {
		mockMvc.perform(get("/api/users/view")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.ALL))
				.andExpect(status().isOk());
	}
	@Test
	void CreateProvider_whenCreateprovider_thenStatus200() throws Exception {
			Provider us = new Provider();
			us.setNameprovider("bob");
			us.setAddress("Qwerty1234");
			us.setPhone("+8(900)345-51-67");

			Mockito.when(providerRepositorie.save(us)).thenReturn(Optional.of(us).orElseThrow());


			mockMvc.perform(post("/api/provider")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.ALL)
					.content(objectMapper.writeValueAsString(us)))
					.andExpect(status().isOk());
	}


}
