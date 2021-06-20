package com.ari.tedeschi.departments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ari.tedeschi.departments.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>{

}
