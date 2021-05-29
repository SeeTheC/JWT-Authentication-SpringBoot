package com.seethec.api.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.seethec.api.services.MyUserDetailService;
import com.seethec.api.util.JwtUtil;
import com.sun.net.httpserver.Filter.Chain;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	@Autowired
	MyUserDetailService mUsrServ;
	
	@Autowired
	private JwtUtil mJwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest pRequest, HttpServletResponse pResponse, FilterChain pFilterChain)
			throws ServletException, IOException {
		
		final String lAuthHeader = pRequest.getHeader("Authorization");
		
		String lUsr = null;
		String lJwt = null;
		
		if (lAuthHeader != null && lAuthHeader.startsWith("Bearer ")) {
			lJwt = lAuthHeader.substring(7);
			lUsr = mJwtUtil.extractUsername(lJwt);
		}
		
		if(lUsr != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			UserDetails lUserDetail = mUsrServ.loadUserByUsername(lUsr);
			
			if( mJwtUtil.validateToken(lJwt, lUserDetail)) {
				UsernamePasswordAuthenticationToken lUsrPwdAuth = new UsernamePasswordAuthenticationToken(lUserDetail, 
						null, lUserDetail.getAuthorities());
				lUsrPwdAuth.setDetails(new WebAuthenticationDetailsSource().buildDetails(pRequest));
				SecurityContextHolder.getContext().setAuthentication(lUsrPwdAuth);
			}
		}
		
		pFilterChain.doFilter(pRequest, pResponse);
		
	}
}
