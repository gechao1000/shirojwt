package com.example.demo.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * JwtToken class
 *
 * @author gexc
 * @date 2019/06/05
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
