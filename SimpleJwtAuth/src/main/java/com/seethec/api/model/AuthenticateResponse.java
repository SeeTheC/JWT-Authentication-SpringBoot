package com.seethec.api.model;

public class AuthenticateResponse {
	
	private String jwt;

	public AuthenticateResponse() {
		super();
	}
	
	public AuthenticateResponse(String jwt) {
		this.jwt = jwt;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	
}
