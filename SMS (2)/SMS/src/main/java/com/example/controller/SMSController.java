package com.example.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.dto.SmsPojo;
import com.example.service.SmsService;


@RestController
public class SMSController{
	@Autowired
	SmsService service;
	@Autowired
	private SimpMessagingTemplate webSocket;
	private final String TOPIC_DESTINATION="/lesson/sms";
	@PostMapping("/mobileNo")
	public ResponseEntity<Boolean> smsSubmit(@RequestBody SmsPojo sms){
		try {
			System.out.println("hello");
			service.send(sms);
			System.out.println("hello");
		}
		catch(Exception e)
		{
			return new ResponseEntity<Boolean>(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		webSocket.convertAndSend(TOPIC_DESTINATION,getTimeStamp()+":SMS has been sent!:"+sms.getPhoneNo());
		return new ResponseEntity<Boolean>(true,HttpStatus.OK);
	}
	@RequestMapping(value="/smscallback",method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	private String getTimeStamp() {
		return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
		
	}
	
	
	
}