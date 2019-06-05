package com.example.demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.compression.DefaultCompressionCodecResolver;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * JwtUtil class
 *
 * @author gexc
 * @date 2019/06/05
 */
public class JwtUtil {

    public static final String SECRET_KEY = "?::4343fdf4fdf6cvf):";

    private JwtUtil() {}

    public static String issueJWT() {
        long timeMillis = System.currentTimeMillis();
        byte[] appKey = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        // 10s过期
        JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuer(UUID.randomUUID().toString())
                .setSubject("SPJZ123")
                .setIssuer("token-server")
                .setIssuedAt(new Date(timeMillis))
                .setExpiration(new Date(timeMillis + 10 * 1000))
                .claim("SHOPCODE", "000001")
                .claim("GHDWDM", "000825")
                .compressWith(CompressionCodecs.DEFLATE)
                .signWith(SignatureAlgorithm.HS512, appKey);
        return jwtBuilder.compact();
    }

    public static Map parserJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
}
