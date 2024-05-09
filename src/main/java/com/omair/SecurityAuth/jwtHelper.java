package com.omair.SecurityAuth;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Component
public class jwtHelper {
    public static final long jwt_exp = 5*60*60;
    public String secret_key = "omair@123";

   

    //Get Username
    public String getUsername(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secret_key).parseClaimsJws(token).getBody();
    }

    private Date getExpDate(String token){
    return getClaimFromToken(token, Claims::getExpiration); 
    } 

    public Boolean isExpireToken(String token){
        final Date expire = getExpDate(token);
        return expire.before(new Date());
    }

    public String generateToken(UserDetails userDetails){
     Map<String, Object> claims = new HashMap<>();
     return doGenerateToken(claims,userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String user){
        return Jwts.builder().setClaims(claims).setSubject(user).setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis()+ jwt_exp*1000))
        .signWith(SignatureAlgorithm.HS512, secret_key).compact();
    }
    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = getUsername(token);
        return (username.equals(userDetails.getUsername()) && !isExpireToken(token));
    }
}
