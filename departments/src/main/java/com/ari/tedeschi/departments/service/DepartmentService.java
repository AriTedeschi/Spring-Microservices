package com.ari.tedeschi.departments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ari.tedeschi.departments.entity.Department;
import com.ari.tedeschi.departments.repository.DepartmentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DepartmentService {
	@Autowired DepartmentRepository repository;

	public Department insert(Department department) {
		log.info("DepartmentService | insert | inserting department");
		department = repository.save(department);
		
		log.info("DepartmentService | insert | department was saved");
		return department;
	}

	public Department getById(Long id) {
		log.info("DepartmentService | getById | Searching with id: "+id);
		Department department = repository.findById(id).orElseThrow(() -> {
			log.info("DepartmentService | getById | None department was found with id: "+id);
			return new ResponseStatusException(HttpStatus.NOT_FOUND, "");
		});
		
		log.info("DepartmentService | getById | Department was found");
		return department;
	}
	
	
}
