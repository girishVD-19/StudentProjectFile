package com.example.student.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class JwtHelper {
	private static final Logger logger = LoggerFactory.getLogger(JwtHelper.class);


    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${JWT_SECRET}")
    private String secretKey;
    
    private Key key;
 
    public String getSecretKey() {
		return secretKey;
	}


	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}


	public Key getKey() {
		return key;
	}


	public void setKey(Key key) {
		this.key = key;
	}


	public static long getJwtTokenValidity() {
		return JWT_TOKEN_VALIDITY;
	}

	
	@PostConstruct
	public void init() {
	    logger.info("Initializing JwtHelper...");

	    if (secretKey == null || secretKey.length() < 32) {
	        throw new IllegalArgumentException("JWT secret key must be at least 32 characters long. Actual: " + (secretKey == null ? "null" : secretKey.length()));
	    }

	    this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
	    logger.info("JWT key initialized successfully.");
	}

	public Integer extractUserId(String token) {
        Claims claims = extractClaims(token);
        
        Object userIdObj = claims.get("userId");
        System.out.println( "Token Object Integer:"+userIdObj);

        if (userIdObj instanceof Integer) {
            return (Integer) userIdObj;
        } else if (userIdObj instanceof String) {
            return Integer.valueOf((String) userIdObj);
        } else if (userIdObj instanceof Number) {
            return ((Number) userIdObj).intValue();
        } else {
            throw new IllegalArgumentException("Invalid or missing userId in token");
        }
    }
    // Method to extract claims from the token
	private Claims extractClaims(String token) {
	    try {
	        return Jwts.parserBuilder()
	                   .setSigningKey(key)  // Use the initialized key
	                   .build()
	                   .parseClaimsJws(token)
	                   .getBody();
	    } catch (JwtException | IllegalArgumentException e) {
	        // Handle invalid token
	        logger.error("Error extracting claims from token", e);
	        return null;  // Return null if there was an issue
	    }
	}

    public String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);  // Remove "Bearer " prefix
        }
        return null;
    }



    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    

    private Claims extractAllClaims(String token) {
    	System.out.println("JWT Secret: " + secretKey);
    	 return Jwts.parserBuilder()
                 .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                 .build()
                 .parseClaimsJws(token)
                 .getBody();
  
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails, Integer userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities()
                .stream()
                .map(auth -> auth.getAuthority())
                .toList());
        claims.put("userId", userId); // ✅ add userId to token
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = getUsernameFromToken(token);  // Extracts username from the token
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));  // Validates user & expiration
        } catch (JwtException | IllegalArgumentException e) {
            // This will catch token parsing issues, including incorrect signature
            logger.error("Invalid JWT token", e);
            return false;
        }
    }

}