package com.seethec.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seethec.api.model.AuthenticateRequest;
import com.seethec.api.model.AuthenticateResponse;
import com.seethec.api.services.MyUserDetailService;
import com.seethec.api.util.JwtUtil;

@RestController
public class HelloController {

	@Autowired
	private AuthenticationManager mAuthMng;
	
	@Autowired
	private MyUserDetailService mUsrServ;
	
	@Autowired
	private JwtUtil mJwtUtil;
	
	@RequestMapping({"/hello"})
	public String hello() {
		return "Hello World";
	}
	
	@PostMapping("/auth")
	public ResponseEntity<?> creatAuthToken(@RequestBody AuthenticateRequest pReq) throws Exception {
		try {
			
			mAuthMng.authenticate( new UsernamePasswordAuthenticationToken(pReq.getUsername(), pReq.getPassword()));
	
		}catch(BadCredentialsException ex) {
			throw new Exception("Incorrect Username and Password",ex);
		}
		
		final UserDetails lusr = mUsrServ.loadUserByUsername(pReq.getUsername());
		
		final String lToken = mJwtUtil.generateToken(lusr);
		
		return ResponseEntity.ok(new AuthenticateResponse(lToken));
	
	}
}
