package com.example.assignmentjavabootcamp.utils.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response{
	Object data;
	String message;

	public Response(Object data, String message) {
		this.data = data;
		this.message = message;
	}
}