package com.example.service;

import java.text.ParseException;

import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import com.example.dto.SmsPojo;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Component
public class SmsService{
	private final String ACCOUNT_SID="AC59ce48466650dd4c07ac1e4b1a94f643";
    private final String AUTH_TOKEN="3c7356fef4076c631320b3a3e10602b3";
    private final String FROM_NUMBER="+16505607417";
    public void send(SmsPojo sms) throws ParseException
    {
    	Twilio.init(ACCOUNT_SID,AUTH_TOKEN);
    	int min=100;
    	int max=999;
    	int num=(int)(Math.random()*(max-min+1)+min);
    	
    	String msg="Dear customer, Your ordered item: "+num+" is ready!!!  Feed your cravings ";
    	Message message=Message.creator(new PhoneNumber(sms.getPhoneNo()),new PhoneNumber(FROM_NUMBER),msg).create();
    }

    
}