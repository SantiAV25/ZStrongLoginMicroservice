package com.ZstrongLoginService.ZstrongLoginMicroServices.config.filter;

import java.io.IOException;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ZstrongLoginService.ZstrongLoginMicroServices.util.jwtUtils.JwtUtils;
import com.auth0.jwt.interfaces.DecodedJWT;


import java.util.Collection;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * This is the JwtTokenValidator Class that will validate the JWT Token 
 * @Author Santiago Agredo Vallejo
 * @Version 1.0
 */

public class JwtTokenValidator extends OncePerRequestFilter{

    //Class Attributes
    private JwtUtils jwtUtils;

    public JwtTokenValidator(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    /**
     * This Method will validate the JWT Token and add to the security context the user, this class is a filter
     * that is added to the security filter chain
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        System.out.println("Always is entering here");


        if(jwtToken != null){
            jwtToken = jwtToken.substring(7);
            DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);
            String username = jwtUtils.exctractUserName(decodedJWT);
            String authorities = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString();
            
            Collection<? extends GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            Authentication authenticateAction = new UsernamePasswordAuthenticationToken(username, null, authorityList);
            securityContext.setAuthentication(authenticateAction);
            SecurityContextHolder.setContext(securityContext);


        }

        filterChain.doFilter(request, response);
    }
    
    
}
