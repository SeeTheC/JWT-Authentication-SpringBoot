package com.seethec.api.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	
	private final String SECRET_KEY = "MY_SCRET_KEY_!786!";
	
	public String extractUsername(String pToken) {
		return  extractClaim(pToken, Claims::getSubject);
	}
	
	private Date extractExpiration(String pToken) {
		return extractClaim(pToken, Claims::getExpiration);
	}
	
	private Claims extractAllClaim(String pToken) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(pToken).getBody();
	}
	
	private <T> T extractClaim(String pToken, Function<Claims, T> pClaimResolver) {
		final Claims lClaim = extractAllClaim(pToken);
		return pClaimResolver.apply(lClaim);
	}


	private String createToken(Map<String, Object> pClaim, String pSubject) {
		long lcurrentTimMilli = System.currentTimeMillis();
		long lExpireDurMilli  = 1000*60*60*10;

		JwtBuilder jwt = Jwts.builder();
		jwt.setClaims(pClaim);
		jwt.setSubject(pSubject);
		jwt.setIssuedAt(new Date(lcurrentTimMilli));
		jwt.setExpiration(new Date(lcurrentTimMilli +  lExpireDurMilli));
		jwt.signWith(SignatureAlgorithm.HS256, SECRET_KEY);
		
		return jwt.compact();
				
	}
	
	private Boolean isTokenValid(String pToken) {
		return extractExpiration(pToken).before(new Date());
	}

	public String generateToken(UserDetails pUserDetails) {
		Map<String,Object> claim = new HashMap<>();
		return createToken(claim, pUserDetails.getUsername());
	}
	
	public Boolean validateToken(String pToken, UserDetails pUserDetails) {
		final String lUsr = extractUsername(pToken);
		return !isTokenValid(pToken) && (lUsr.equals(pUserDetails.getUsername()));
		
	}
	
}
