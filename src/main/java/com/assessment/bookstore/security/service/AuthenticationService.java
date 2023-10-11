package com.assessment.bookstore.security.service;


import com.assessment.bookstore.security.model.AuthenticationRequest;
import com.assessment.bookstore.security.model.AuthenticationResponse;
import com.assessment.bookstore.security.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	public AuthenticationResponse authenticateUser(AuthenticationRequest authenticationRequest) {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		String token = jwtTokenUtil.generateToken(userDetails);

		return new AuthenticationResponse(token);
	}

	private void authenticate(String email, String password) {

		try {

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

		}  catch (BadCredentialsException e) {

			throw new BadCredentialsException("invalid credenatials", e);

		}
	}

}
