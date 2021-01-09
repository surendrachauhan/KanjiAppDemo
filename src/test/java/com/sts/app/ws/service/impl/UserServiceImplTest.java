package com.sts.app.ws.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sts.app.ws.io.entity.UserEntity;
import com.sts.app.ws.io.repositories.UserRepository;
import com.sts.app.ws.ui.model.ws.shared.dto.UserDto;

class UserServiceImplTest {

	@InjectMocks
	UserServiceImpl userService;

	@Mock
	UserRepository userRepository;

	//@Mock
	//Utils utils;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testGetUser() {

		UserEntity userEntity = new UserEntity();

		userEntity.setId(1L);
		userEntity.setFirstName("Surendra");
		userEntity.setUserId("dfdfdfdf42424");

		when(userRepository.findByEmail(Mockito.anyString())).thenReturn(userEntity);

		UserDto userDto = userService.getUser("test@gmail.com");

		assertNotNull(userDto);
		assertEquals("Surendra", userDto.getFirstName());
	}

	@Test
	final void testGetUser_UserNameNotFoundException() {
		when(userRepository.findByEmail(Mockito.anyString())).thenReturn(null);

		assertThrows(UsernameNotFoundException.class, () -> userService.getUser("test@gmail.com"));
	}
}
