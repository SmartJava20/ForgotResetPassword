package com.smartjava.demo.service;

import java.util.Optional;
import java.util.Properties;
import java.util.Random;

import javax.swing.plaf.basic.BasicGraphicsUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.smartjava.demo.model.StudentModel;
import com.smartjava.demo.repository.StudentRepository;
import com.smartjava.demo.response.MetaDataResponse;
import com.smartjava.demo.response.ResultResponse;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class StudentService {

	@Autowired
	private StudentRepository sRepository;
	private JavaMailSender mailSender;

	
	  @Autowired
	    public StudentService(JavaMailSender mailSender) {
	        this.mailSender = mailSender;
	        
	    }

	
	public ResultResponse getList() {
		MetaDataResponse metadataResponse = new MetaDataResponse();
		ResultResponse resultResponse = new ResultResponse();
		try {
			metadataResponse.setCode("200K");
			metadataResponse.setMessage("Get all records from database");
			metadataResponse.setNoOfRecords(sRepository.findAll().size());
			resultResponse.setMetadata(metadataResponse);
			resultResponse.setResult(sRepository.findAll());
			return resultResponse;
		} catch (Exception e) {
			e.printStackTrace();
			metadataResponse.setCode("400K");
			metadataResponse.setMessage("Failed to fetch reords");
			metadataResponse.setNoOfRecords(0);
			resultResponse.setMetadata(metadataResponse);
			resultResponse.setResult(null);
			return resultResponse;

		}
	}

	public ResultResponse save(StudentModel suModel) {
		MetaDataResponse metadataResponse = new MetaDataResponse();
		ResultResponse resultResponse = new ResultResponse();
		try {

			StudentModel studentSave = sRepository.save(suModel);

			metadataResponse.setCode("200K");
			metadataResponse.setMessage("save Data sucessfully in database");
			metadataResponse.setNoOfRecords(studentSave.getId());
			resultResponse.setMetadata(metadataResponse);
			resultResponse.setResult(studentSave);
			return resultResponse;
		} catch (Exception e) {
			e.printStackTrace();
			metadataResponse.setCode("400K");
			metadataResponse.setMessage("Failed to save records in database");
			metadataResponse.setNoOfRecords(0);
			resultResponse.setMetadata(metadataResponse);
			resultResponse.setResult(null);
			return resultResponse;

		}
	}

	public ResultResponse byId(int id) {
		MetaDataResponse metadataResponse = new MetaDataResponse();
		ResultResponse resultResponse = new ResultResponse();
		try {

			metadataResponse.setCode("200K");
			metadataResponse.setMessage("get data from data base by id");
			metadataResponse.setNoOfRecords(1);
			resultResponse.setMetadata(metadataResponse);
			resultResponse.setResult(sRepository.findById(id));
			return resultResponse;
		} catch (Exception e) {
			e.printStackTrace();
			metadataResponse.setCode("400K");
			metadataResponse.setMessage("fail to fetch records from database");
			metadataResponse.setNoOfRecords(0);
			resultResponse.setMetadata(metadataResponse);
			resultResponse.setResult(null);
			return resultResponse;

		}
	}

	public ResultResponse update(StudentModel suModel, int id) {
		MetaDataResponse metadataResponse = new MetaDataResponse();
		ResultResponse resultResponse = new ResultResponse();
		try {
			Optional<StudentModel> studeUpdate = sRepository.findById(id);
			StudentModel updateStud = studeUpdate.get();

			updateStud.setStudName(suModel.getStudName());
			updateStud.setAge(suModel.getAge());
			updateStud.setEmail(suModel.getEmail());
			StudentModel studentSave = sRepository.save(updateStud);

			metadataResponse.setCode("200K");
			metadataResponse.setMessage("student update sucessfully");
			metadataResponse.setNoOfRecords(1);
			resultResponse.setMetadata(metadataResponse);
			resultResponse.setResult(studentSave);
			return resultResponse;
		} catch (Exception e) {
			e.printStackTrace();
			metadataResponse.setCode("400K");
			metadataResponse.setMessage("failed to updated records in database");
			metadataResponse.setNoOfRecords(0);
			resultResponse.setMetadata(metadataResponse);
			resultResponse.setResult(null);
			return resultResponse;

		}

	}

	public ResultResponse delete(int id) {
		MetaDataResponse metadataResponse = new MetaDataResponse();
		ResultResponse resultResponse = new ResultResponse();
		try {

			sRepository.deleteById(id);

			metadataResponse.setCode("200K");
			metadataResponse.setMessage("delete records sucessfully");
			metadataResponse.setNoOfRecords(1);
			resultResponse.setMetadata(metadataResponse);
			resultResponse.setResult(null);
			return resultResponse;
		} catch (Exception e) {
			e.printStackTrace();
			metadataResponse.setCode("400K");
			metadataResponse.setMessage("fail to delete records from database");
			metadataResponse.setNoOfRecords(0);
			resultResponse.setMetadata(metadataResponse);
			resultResponse.setResult(null);
			return resultResponse;

		}

	}

	public ResultResponse verifyEmail(String email) {
		MetaDataResponse metadataResponse = new MetaDataResponse();
		ResultResponse resultResponse = new ResultResponse();
		try {

			StudentModel studentModel = sRepository.findByEmail(email);
			String emailVale = studentModel.getEmail();

			if (emailVale.equals(email)) {
				metadataResponse.setCode("200K");
				metadataResponse.setMessage("email is valid");
				metadataResponse.setNoOfRecords(1);
				resultResponse.setMetadata(metadataResponse);
				resultResponse.setResult(null);
			}
			return resultResponse;
		} catch (Exception e) {
			metadataResponse.setCode("400K");
			metadataResponse.setMessage("email is not valid");
			metadataResponse.setNoOfRecords(0);
			resultResponse.setMetadata(metadataResponse);
			resultResponse.setResult(null);
			return resultResponse;
		}

	}

	public ResultResponse sendOtp(String email) {
		MetaDataResponse metadataResponse = new MetaDataResponse();
		ResultResponse resultResponse = new ResultResponse();
		try {

			Random random = new Random();
			int optValue=100000 + random.nextInt(900000);
			
			String subject="Your OTP for email verfication";
			String text="Verify your otp"+optValue;
			
			sendEmail(subject,text,email);
			
			StudentModel studentModel=sRepository.findByEmail(email);
			studentModel.setOtp(optValue);
			sRepository.save(studentModel);

			metadataResponse.setCode("200K");
			metadataResponse.setMessage("OTP send sucessfully");
			metadataResponse.setNoOfRecords(1);
			resultResponse.setMetadata(metadataResponse);
			resultResponse.setResult(studentModel);
			return resultResponse;
		} catch (Exception e) {
			e.printStackTrace();
			metadataResponse.setCode("400K");
			metadataResponse.setMessage("fail to send otp");
			metadataResponse.setNoOfRecords(0);
			resultResponse.setMetadata(metadataResponse);
			resultResponse.setResult(null);
			return resultResponse;

		}

	}

	

	
	  private void sendEmail(String email, String subject, String text) {
		  String to =email;

	        // Sender's email ID needs to be mentioned
	        String from = "ABC@gmail.com";

	        String password = "*********";

	        // Assuming you are sending email from Gmail
	        String host = "smtp.gmail.com";

	        // Get system properties
	        Properties properties = System.getProperties();

	        // Setup mail server
	        properties.setProperty("mail.smtp.host", host);
	        properties.setProperty("mail.smtp.port", "587");
	        properties.setProperty("mail.smtp.auth", "true");
	        properties.setProperty("mail.smtp.starttls.enable", "true");

	        // Get the default Session object.
	        Session session = Session.getInstance(properties, new Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(from, password);
	            }
	        });

	        try {
	            // Create a default MimeMessage object.
	            MimeMessage message = new MimeMessage(session);

	            // Set From: header field of the header.
	            message.setFrom(new InternetAddress(from));

	            // Set To: header field of the header.
	            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	            // Set Subject: header field
	            message.setSubject(subject);

	            // Now set the actual message
	            message.setText(text);

	            // Send message
	            Transport.send(message);
	            System.out.println("Sent message successfully....");
	        } catch (MessagingException mex) {
	            mex.printStackTrace();
	        }
	    }
	    


	public ResultResponse verifyOTP(String email, int otp) {
		MetaDataResponse metadataResponse = new MetaDataResponse();
		ResultResponse resultResponse = new ResultResponse();
		try {

			StudentModel studentModel = sRepository.findByEmail(email);
			int otpValue=studentModel.getOtp();

			if (otp ==otpValue ) {
				metadataResponse.setCode("200K");
				metadataResponse.setMessage("otp is valid");
				metadataResponse.setNoOfRecords(1);
				resultResponse.setMetadata(metadataResponse);
				resultResponse.setResult(studentModel);
			}
			return resultResponse;
		} catch (Exception e) {
			metadataResponse.setCode("400K");
			metadataResponse.setMessage("otp is not valid");
			metadataResponse.setNoOfRecords(0);
			resultResponse.setMetadata(metadataResponse);
			resultResponse.setResult(null);
			return resultResponse;
		}
	}


	public ResultResponse passUpdate(StudentModel suModel) {
		MetaDataResponse metadataResponse = new MetaDataResponse();
		ResultResponse resultResponse = new ResultResponse();
		try {
			
			StudentModel stud=sRepository.findByEmail(suModel.getEmail());
			stud.setPassword(suModel.getPassword());
			sRepository.save(stud);
			

				metadataResponse.setCode("200K");
				metadataResponse.setMessage("paassword update succesfully");
				metadataResponse.setNoOfRecords(1);
				resultResponse.setMetadata(metadataResponse);
				resultResponse.setResult(stud);
			
			return resultResponse;
		} catch (Exception e) {
			metadataResponse.setCode("400K");
			metadataResponse.setMessage("failed to update password");
			metadataResponse.setNoOfRecords(0);
			resultResponse.setMetadata(metadataResponse);
			resultResponse.setResult(null);
			return resultResponse;
		}
	}

}
