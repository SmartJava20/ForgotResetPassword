package com.smartjava.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.smartjava.demo.model.StudentModel;
import com.smartjava.demo.response.ResultResponse;
import com.smartjava.demo.service.StudentService;

@RestController
@RequestMapping("/api/v1")
public class StudentController {
	
	@Autowired
    private StudentService service;
	
	@GetMapping("/list")
	public ResultResponse getList() {
		return service.getList();
	} 
	
	@GetMapping("/by/{id}")
	public ResultResponse byId(@PathVariable ("id") int id) {
		return service.byId(id);
	} 
	
	

	@PostMapping("/save")
	public ResultResponse save(@RequestBody StudentModel suModel) {
		return service.save(suModel);
	} 
	
	@PutMapping("/update/{id}")
	public ResultResponse update(@RequestBody StudentModel suModel,@PathVariable ("id") int id) {
		return service.update(suModel,id);
	} 
	
	@DeleteMapping("/delete/{id}")
	public ResultResponse delete(@PathVariable ("id") int id) {
		return service.delete(id);
	} 
	
	@PostMapping("/verifyEmail")
	public ResultResponse verifyEmail(@RequestBody StudentModel suModel) {
		
		String email=suModel.getEmail();
		
		return service.verifyEmail(email);
	}
	
	
	@PostMapping("/sendOTP")
	public ResultResponse sendOtp(@RequestBody StudentModel suModel) {
		String email=suModel.getEmail();
		return service.sendOtp(email);
	}
	
	@PostMapping("/verifyOTP")
	public ResultResponse verifyOTP(@RequestBody StudentModel suModel) {
		
		String email=suModel.getEmail();
		int otp=suModel.getOtp();
		
		return service.verifyOTP(email,otp);
	}
	
	@PutMapping("/passwordUpdate")
	public ResultResponse passUpdate(@RequestBody StudentModel suModel) {
		return service.passUpdate(suModel);
	}
	
	

}
