package com.ZstrongLoginService.ZstrongLoginMicroServices.util.jwtUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;
import java.util.Date;
import java.util.UUID;
import java.util.Map;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;


import lombok.val;

/**
 * This is the JwtUtils Class that will handle the JWT Token
 * @Author Santiago Agredo Vallejo
 * @Version 1.0
 */
@Component
public class JwtUtils {

    @Value("${secutiry.jwt.key.private}")
    private String privateKey;

    @Value("${secutiry.jwt.user.generator}")
    private String userGenerator;

    /**
     * This Method will create a JWT Token
     * @param authentication
     * @return String
     */
    public String createToken(Authentication authentication) {
       Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
       String username = authentication.getPrincipal().toString();
       String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

       String jwtToken = JWT.create()
       .withIssuer(this.userGenerator)
       .withSubject(username)
       .withClaim("authorities", authorities)
       .withIssuedAt(new Date())
       .withExpiresAt(new Date(System.currentTimeMillis() + 1800000))
       .withJWTId(UUID.randomUUID().toString())
       .sign(algorithm);

       return jwtToken;

    }

    /**
     * This Method will validate the JWT Token
     * @param token
     * @return
     */
    public DecodedJWT validateToken(String token) {
       try{

        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(this.userGenerator).build();

        DecodedJWT decodedJWT = verifier.verify(token);

        return decodedJWT;

       }catch(JWTVerificationException exception){
           throw new JWTVerificationException("Token is invalid, no Authorazied");
       }
    }

    /**
     * This Method will extract the username from the JWT Token
     * @param decodedJWT
     * @return String
     */
    public String exctractUserName(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject().toString();
    }

    /**
     * This Method will get a specific claim from the JWT Token
     * @param decodedJWT
     * @param claimName
     * @return Claim
     */
    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {
        return decodedJWT.getClaim(claimName);
    }

    /**
     * This Method will get all the claims from the JWT Token
     * @param decodedJWT
     * @return Map<String, Claim>
     */
    public Map<String, Claim> getAllClaims(DecodedJWT decodedJWT) {
        return decodedJWT.getClaims();
    }   

  

    
}
