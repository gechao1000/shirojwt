package com.example.demo.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * JwtFilter class
 *
 * @author gexc
 * @date 2019/06/05
 */
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {

    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        return this.getAuthzHeader(request) != null;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                // token是否有效
                return this.executeLogin(request, response);
            } catch (Exception e) {
                log.error(e.getMessage());
                return false;
            }
        } else {
            // 没有 token
            HttpServletRequest req = (HttpServletRequest) request;
            log.info("当前请求 {} Authorization属性(Token)为空 请求类型 {}", req.getRequestURI(), req.getMethod());
            return false;
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        this.sendChallenge(request, response);
        return false;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        JwtToken token = new JwtToken(this.getAuthzHeader(request));
        this.getSubject(request,response).login(token);
        return true;
    }
}
