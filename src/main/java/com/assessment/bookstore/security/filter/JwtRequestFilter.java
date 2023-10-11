package com.assessment.bookstore.security.filter;


import com.assessment.bookstore.security.service.JwtUserDetailsService;
import com.assessment.bookstore.security.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtRequestFilter.class);

    public static final String JWT_TOKEN_AUTHORIZATION_HEADER_KEY = "Authorization";
    public static final String JWT_TOKEN_AUTHORIZATION_PREFIX = "Bearer ";

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String usernameFromRequest = getUsernameFromRequest(request);


        if (usernameFromRequest != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(usernameFromRequest);


            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


        }
        filterChain.doFilter(request, response);

    }

    private String getUsernameFromRequest(HttpServletRequest request) {

        final String requestTokenHeader = request.getHeader(JWT_TOKEN_AUTHORIZATION_HEADER_KEY);

        return jwtTokenUtil.getUsernameFromAuthorizationHeader(requestTokenHeader);
    }
}
