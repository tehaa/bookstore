package com.assessment.bookstore.model;

public class ErrorResponseDto {

	private int statusCode;
	private String description;
	private Object content;

	public ErrorResponseDto() {
		super();
	}

	public ErrorResponseDto(int statusCode, String description) {
		super();
		this.statusCode = statusCode;
		this.description = description;
	}

	public ErrorResponseDto(int statusCode, String description, Object content) {
		super();
		this.statusCode = statusCode;
		this.description = description;
		this.content = content;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}
}
