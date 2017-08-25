package com.dayuanit.atm.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AjaxResultDTO {
	
	private boolean success;//boolean 写错了
	
	private String message;
	
	private Object data;
	
	public AjaxResultDTO() {
		
	}
	
	public static void main(String[] args) {
		ObjectMapper om = new ObjectMapper();
		
		String json;
		try {
			json = om.writerWithDefaultPrettyPrinter().writeValueAsString(AjaxResultDTO.success());
			System.out.println(json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}
	
	public AjaxResultDTO(boolean success, String message, Object data) {
		this.success = success;
		this.message = message;
		this.data = data;
	}
	
	public static AjaxResultDTO success() {//如果成功失败各需要传送什么信息
		return new AjaxResultDTO(true, null , null);
	}
	
	public static AjaxResultDTO success(Object data) {//如果成功失败各需要传送什么信息
		return new AjaxResultDTO(true, null , data);
	}
	
	public static AjaxResultDTO failed(String message) {
		return new AjaxResultDTO(false, message , null);
	}
	
	public boolean getSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
}
