package com.jobportal.utility;

public class Data {

	public static String getMessageBody(String otpCode, String name) {
	    return "<!DOCTYPE html>"
	    	    + "<html>"
	    	    + "<head>"
	    	    + "  <meta charset='UTF-8'>"
	    	    + "  <title>Your OTP Code</title>"
	    	    + "  <style>"
	    	    + "    body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f4f7; margin: 0; padding: 0; }"
	    	    + "    .container { background-color: #ffffff; max-width: 600px; margin: 40px auto; padding: 30px; border-radius: 12px; box-shadow: 0 2px 6px rgba(0,0,0,0.1); }"
	    	    + "    .header { font-size: 20px; font-weight: 600; color: #333333; margin-bottom: 20px; }"
	    	    + "    .otp-code { font-size: 32px; font-weight: bold; letter-spacing: 4px; color: #1a73e8; margin: 20px 0; }"
	    	    + "    .message { font-size: 16px; color: #555555; }"
	    	    + "    .footer { font-size: 12px; color: #999999; margin-top: 40px; text-align: center; }"
	    	    + "  </style>"
	    	    + "</head>"
	    	    + "<body>"
	    	    + "  <div class='container'>"
	    	    + "    <div class='header'>Your One-Time Password (OTP)</div>"
	    	    + "    <div class='message'>"
	    	    + "      Hello, "+name +"<br><br>"
	    	    + "      Use the OTP code below to complete your login or verification process:"
	    	    + "    </div>"
	    	    + "    <div class='otp-code'>" + otpCode + "</div>"
	    	    + "    <div class='message'>"
	    	    + "      This OTP is valid for 5 minutes. Please do not share it with anyone."
	    	    + "    </div>"
	    	    + "    <div class='footer'>"
	    	    + "      If you did not request this, please ignore this email.<br><br>"
	    	    + "      &copy; 2025 Job Hook Inc. All rights reserved."
	    	    + "    </div>"
	    	    + "  </div>"
	    	    + "</body>"
	    	    + "</html>";
	}



}
