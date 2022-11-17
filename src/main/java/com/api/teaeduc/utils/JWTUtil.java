package com.api.teaeduc.utils;

import java.util.Date;

import com.api.teaeduc.dtos.JwtDTO;
import com.api.teaeduc.dtos.UsuarioDTO;
import com.api.teaeduc.exception.UnauthozedBusinessException;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;

public class JWTUtil {
	
	private JWTUtil () {}

	@Getter @Setter
	private static Long expiration;

	private static final String API_KEY = "89eeacc2e54345d58747ddb598ba9bfsaddsadssadasasdasdasdasdasdasdas@@@AAABBB";

	static Algorithm algorithmHS = Algorithm.HMAC256(API_KEY);

	public static final String BEARER = "Bearer";

	static String message;

	public static JwtDTO encode(UsuarioDTO usuarioDTO) throws JsonProcessingException {
		String token = Jwts.builder().setSubject(usuarioDTO.getEmail())
		.setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(SignatureAlgorithm.HS512, API_KEY.getBytes())
        .compact();

		JwtDTO jwt = new JwtDTO();
		jwt.setJwt(token);
	
		return jwt;
	}

	public static JwtDTO refreshToken(String token, UsuarioDTO usuarioDTO) throws BusinessException, JsonProcessingException {
		String tokenSemBearer = token.substring(BEARER.length()).trim();
		if (Boolean.TRUE.equals(tokenValido(tokenSemBearer))) {
			return encode(usuarioDTO);
		} else {
			throw new UnauthozedBusinessException();
		}
	}

	public static Boolean tokenValido(String token) throws UnauthozedBusinessException {
		if (token != null && !token.equals("") && token.startsWith(BEARER)) {
			token = token.replace(BEARER + " ", "");
		}

		Jws<Claims> claims = getClaims(token);
		if (claims != null) {
			String username = claims.getBody().getSubject();
			Date expirationDate = claims.getBody().getExpiration();
			Date now = new Date(System.currentTimeMillis());
			if (username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}
		
		return false;
	}

	public static String getUsername(String token) throws UnauthozedBusinessException {
		if (token != null && !token.equals("") && token.startsWith(BEARER)) {
			token = token.replace(BEARER + " ", "");
		}

		Jws<Claims> claims = getClaims(token);
		if (claims != null) {
			return claims.getBody().getSubject();
		}
		return null;
	}

	private static Jws<Claims> getClaims(String token) throws UnauthozedBusinessException {
		if (token != null && !token.equals("")) {
			return Jwts.parserBuilder().setSigningKey(API_KEY.getBytes()).build().parseClaimsJws(token);
		}
		throw new UnauthozedBusinessException();
	}
}
