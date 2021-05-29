package com.seethec.api.model;

public class AuthenticateRequest {
		private String username;
		private String password;
		
		
		public AuthenticateRequest() {
		}

		public AuthenticateRequest(String pUserName, String pPassword) {
			this.username = pUserName;
			this.password = pPassword;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
		
		
}
