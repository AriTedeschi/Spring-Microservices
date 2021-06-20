package com.ari.tedeschi.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ari.tedeschi.users.entity.User;
import com.ari.tedeschi.users.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	@Autowired UserRepository repository;

	public User insert(User user) {
		log.info("UserService | insert | user department");
		user = repository.save(user);
		
		log.info("UserService | insert | user was saved");
		return user;
	}

	public User getById(Long id) {
		log.info("UserService | getById | Searching with id: "+id);
		User user = repository.findById(id).orElseThrow(() -> {
			log.info("UserService | getById | None user was found with id: "+id);
			return new ResponseStatusException(HttpStatus.NOT_FOUND, "");
		});
		
		log.info("UserService | getById | User was found");
		return user;
	}
}
