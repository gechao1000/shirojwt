package com.example.demo.shiro;

import com.example.demo.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * CustomRealm class
 *
 * @author gexc
 * @date 2019/06/05
 */
@Component
public class CustomRealm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }

        String payload = (String) principals.getPrimaryPrincipal();
//        User user = (User) getAvailablePrincipal(principals);
        Set<String> roles = new LinkedHashSet<>();
        roles.add("admin");
        roles.add("user");

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roles);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (!(token instanceof JwtToken)) {
            return null;
        }
        JwtToken jwtToken = (JwtToken) token;
        String jwt = (String) jwtToken.getCredentials();
        String subject = null;
        try {
            Claims claims = JwtUtil.parserJWT(jwt);
            subject = claims.getSubject();
        } catch (MalformedJwtException e) {
            throw new AuthenticationException("令牌格式错误");
        } catch (Exception e) {
            throw new AuthenticationException("令牌无效");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo("jwt:" + subject, jwt, this.getName());
        return info;
    }
}
