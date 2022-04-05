package com.example.shopspringboot.domain;

import java.time.LocalDateTime;

/**
 * Captcha entity.
 */
public class Captcha {

	private String id;
	private String value;
	private LocalDateTime creationTime;

	public Captcha(String id, String value, LocalDateTime creationTime) {
		this.id = id;
		this.value = value;
		this.creationTime = creationTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public LocalDateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}
}