package com.weekone.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.weekone.model.Employee;

public interface WeekOneService {

	String createEmployeeService(MultipartFile file, String name, long salary);

	List<Employee> getAllEmployeesService();

	Employee getEmployeeByIdService(long id);

	Employee deleteEmployeeByIdService(long id);

	Employee updateEmployeeByIdService(long id, String name, long salary);
}
