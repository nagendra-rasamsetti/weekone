package com.weekone.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.weekone.model.Employee;
import com.weekone.service.WeekOneService;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@ApiResponses(value = { @ApiResponse(code = 200, message = "success"),
		@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		@ApiResponse(code = 409, message = "conflict or duplicte"),
		@ApiResponse(code = 500, message = "Internal server error") })
public class WeekOneController {
	
	Logger log = LoggerFactory.getLogger(WeekOneController.class);
	
	@Autowired
	WeekOneService weekoneservice;
	
	@RequestMapping(value = "/createEmployee", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> uploadFile(@RequestParam(name = "file", required = false) MultipartFile file,@RequestParam(name = "name", required = false) String name,@RequestParam(name = "salary", required = false) long salary,
			HttpServletRequest request) throws IOException {
		log.info("got param file-->" + file+"name-->"+name+"salary-->"+salary);

		Map<String, Object> result = new HashMap<String, Object>();
		log.info(request.getParameterMap().toString());
		String imageURL = weekoneservice.createEmployeeService(file,name,salary);
		result.put("imageURL", imageURL);
		log.info("upload completed and imageURL is-->" + imageURL);
		log.info("upload file completed now returning");
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(result);
	}
	
	
	@RequestMapping(value = "/getAllEmployees", method = RequestMethod.GET)
	public ResponseEntity<Object> uploadFile() throws IOException {
		List<Employee> response = weekoneservice.getAllEmployeesService();
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
	
	@RequestMapping(value = "/getEmployeeById", method = RequestMethod.GET)
	public ResponseEntity<Object> getEmployeeById(@RequestParam(name = "id") long id) {
		log.info("in getEmployeeById service() --> with id --> " + id);

		Employee response = weekoneservice.getEmployeeByIdService(id);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
	@RequestMapping(value = "/updateEmployeeById", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateEmployeeById(@RequestParam(name = "id") long id,@RequestParam(name = "name") String name,@RequestParam(name = "salary") long salary) {
		log.info("in updateEmployeeById service() --> with id --> " + id);

		Employee response = weekoneservice.updateEmployeeByIdService(id,name,salary);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
	@RequestMapping(value = "/deleteEmployeeById", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteEmployeeById(@RequestParam(name = "id") long id) {
		log.info("in deleteEmployeeById service() --> with id --> " + id);

		Employee response = weekoneservice.deleteEmployeeByIdService(id);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
	}
	

}
