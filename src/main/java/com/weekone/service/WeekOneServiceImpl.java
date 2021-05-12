package com.weekone.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.weekone.model.Employee;
import com.weekone.repository.EmployeeRepository;
import com.weekone.worker.AWSWorker;
@Service
public class WeekOneServiceImpl  implements WeekOneService{
	
	Logger log = LoggerFactory.getLogger(WeekOneServiceImpl.class);
	
	@Autowired
	AWSWorker awsworker;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Override
	public String createEmployeeService(MultipartFile file,String name,long salary) {
		File convertFile = new File(file.getOriginalFilename());
		try {
			convertFile.createNewFile();
			FileOutputStream fout = new FileOutputStream(convertFile);
			fout.write(file.getBytes());
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String imageUrl = convertFile.getAbsolutePath();
		imageUrl = awsworker.uploadImageToS3(imageUrl);
		log.info("Image upload completed succesfully");
		convertFile.delete();
		
		Employee emp = new Employee();
		emp.setProfileImage(imageUrl);
		emp.setName(name);
		emp.setSalary(salary);
		employeeRepository.save(emp);

		return imageUrl;
	}

	@Override
	public List<Employee> getAllEmployeesService() {
		List<Employee> employees = employeeRepository.findAll();
		return employees;
	}

	@Override
	public Employee getEmployeeByIdService(long id) {

		 Employee employee = employeeRepository.findById(id);
		return employee;
	}

	@Override
	public Employee deleteEmployeeByIdService(long id) {
		Employee employee = employeeRepository.findById(id);
		employeeRepository.delete(employee);
		return employee;
	}

	@Override
	public Employee updateEmployeeByIdService(long id, String name, long salary) {
		Employee employee = employeeRepository.findById(id);
		employee.setName(name);
		employee.setSalary(salary);
		employee = employeeRepository.save(employee);
		return employee;
	}

}
