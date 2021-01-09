package com.sts.app.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sts.app.ws.exceptions.UserServiceException;
import com.sts.app.ws.io.entity.UserEntity;
import com.sts.app.ws.io.repositories.UserRepository;
import com.sts.app.ws.service.UserService;
import com.sts.app.ws.ui.model.response.ErrorMessage;
import com.sts.app.ws.ui.model.response.ErrorMessages;
import com.sts.app.ws.ui.model.ws.shared.Utils;
import com.sts.app.ws.ui.model.ws.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	Utils utils;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDto createUser(UserDto user) {

		if (userRepository.findByEmail(user.getEmail()) != null)
			throw new RuntimeException("User Already Exists");

		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);

		String publicUserId = utils.generateUserId(30);

		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userEntity.setUserId(publicUserId);
		UserEntity storedValue = userRepository.save(userEntity);

		UserDto returnValue = new UserDto();

		BeanUtils.copyProperties(storedValue, returnValue);

		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity == null)
			throw new UsernameNotFoundException(email);
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDto getUser(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserDto returnValue = new UserDto();

		UserEntity userEntity = userRepository.findByUserId(userId);

		if (userEntity == null)
			throw new UsernameNotFoundException(userId);

		BeanUtils.copyProperties(userEntity, returnValue);

		return returnValue;
	}

	@Override
	public UserDto updateUser(String id, UserDto userDto) {
		UserDto returnValue = new UserDto();

		UserEntity userEntity = userRepository.findByUserId(id);
		
		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		
		userEntity.setFirstName(userDto.getFirstName());
		userEntity.setLastName(userDto.getLastName());
		
		UserEntity updatedValues = userRepository.save(userEntity);
		
		BeanUtils.copyProperties(updatedValues, returnValue);
		
		return returnValue;
	}

	@Override
	public void deleteUser(String userId) {
	UserEntity userEntity = userRepository.findByUserId(userId);
		
		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		
		userRepository.delete(userEntity);
		
	}

	@Override
	public List<UserDto> getUsers(int page, int limit) {
		List <UserDto> returnValue = new ArrayList<>();
		
		Pageable pageable = PageRequest.of(page, limit);
		
		
		Page<UserEntity> userPage =  userRepository.findAll(pageable);
		List<UserEntity> users = userPage.getContent();
		
		for(UserEntity userEntity : users) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(userEntity, userDto);
			returnValue.add(userDto);
        }
		
		return returnValue;
	}

}
