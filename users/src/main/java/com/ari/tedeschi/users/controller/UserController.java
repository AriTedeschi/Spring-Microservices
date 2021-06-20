package com.ari.tedeschi.users.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ari.tedeschi.users.entity.User;
import com.ari.tedeschi.users.service.UserService;
import com.ari.tedeschi.users.vo.Department;
import com.ari.tedeschi.users.vo.ResponseTemplateVO;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
	@Autowired UserService service;
	@Autowired RestTemplate restTemplate;
	
	private static final String CB_SERVICE = "userService";
	
	@PostMapping
	@CircuitBreaker(name=CB_SERVICE, fallbackMethod = "fallback")
	public ResponseEntity<User> insertUser(@RequestBody User user) {
		log.info("UserController | insertUser was called");
		user = service.insert(user);
		
		log.info("User with id: "+user.getId()+" was registred successfully");
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		
		return ResponseEntity.created(uri).body(user);
	}
	
	@GetMapping("/{id}")
	@CircuitBreaker(name=CB_SERVICE, fallbackMethod = "fallback")
	public ResponseEntity<ResponseTemplateVO> getById(@PathVariable("id") Long id) {
		log.info("UserController | getById was called");
		User user = service.getById(id);
		log.info("UserController | getById | user with "+id+" was found and returned");
		
		Department department = restTemplate
				.getForObject("http://DEPARTMENT-SERVICE/departments/"+user.getDepartmentId(),
						Department.class);
		
		
		return ResponseEntity.ok().body(ResponseTemplateVO.from(user, department));
	}
	
	public ResponseEntity<String> fallback(Exception e){
        return new ResponseEntity<String>("Department service is down", HttpStatus.SERVICE_UNAVAILABLE);
    }
}
