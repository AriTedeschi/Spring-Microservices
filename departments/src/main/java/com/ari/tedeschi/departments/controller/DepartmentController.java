package com.ari.tedeschi.departments.controller;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ari.tedeschi.departments.entity.Department;
import com.ari.tedeschi.departments.service.DepartmentService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/departments")
@Slf4j
public class DepartmentController {
	@Autowired DepartmentService service;
	
	private static final String CB_SERVICE = "departmentService";
	
	@PostMapping
	@CircuitBreaker(name=CB_SERVICE, fallbackMethod = "fallback")
	public ResponseEntity<Department> insertDepartment(@RequestBody Department department) {
		log.info("DepartmentController | insertDepartment was called");
		department = service.insert(department);
		
		log.info("Department with id: "+department.getId()+" was registred successfully");
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(department.getId()).toUri();
		
		return ResponseEntity.created(uri).body(department);
	}
	
	@GetMapping("/{id}")
	@CircuitBreaker(name=CB_SERVICE, fallbackMethod = "fallback")
	public ResponseEntity<Department> getById(@PathVariable("id") Long id) {
		log.info("DepartmentController | getById was called");
		Department department = service.getById(id);

		log.info("DepartmentController | getById | department with "+id+" was found and returned");
		return ResponseEntity.ok().body(department);
	}
	
	public ResponseEntity<String> fallback(Exception e){
        return new ResponseEntity<String>("Department service is down", HttpStatus.SERVICE_UNAVAILABLE);
    }
}
