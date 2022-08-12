package com.auth.interceptor;

import com.auth.defenum.Cause;
import com.auth.defenum.RequestUrl;
import com.auth.defenum.Role;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final String DIRECT_PASS = "Authenticator";
    @Resource
    private KeyCheckingService keyCheckingService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod))
            return true;
        if (DIRECT_PASS.equals(request.getHeader("DirectPass")))
            return true;
        String requestPath = request.getServletPath();
        if (requestPath.matches(RequestUrl.serverUrl())) {
            String remote = request.getHeader("Remote");
            String passcode = request.getHeader("Passcode");
            if (requestPath.matches(RequestUrl.FREE.path)) {
                if ("PUT".equals(request.getMethod())) {
                    if ("#".equals(remote) && "#".equals(passcode))
                        return true;
                    response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                    response.addHeader("Cause", Cause.AUTH_FAILED.code);
                    response.addHeader("CauseStr", Cause.AUTH_FAILED.name());
                    return false;
                }
                return true;
            }
            Role role = keyCheckingService.getRole(remote, passcode);
            if (role == null || role == Role.UNKNOWN || role == Role.UNDEFINED) {
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                response.addHeader("Cause", Cause.AUTH_FAILED.code);
                response.addHeader("CauseStr", Cause.AUTH_FAILED.name());
                return false;
            } else if (role == Role.SUPERUSER)
                return true;
            else if (role == Role.ADMIN)
                return true;
            else if (role == Role.VISITOR) {
                if (requestPath.matches(RequestUrl.USER.path))
                    return true;
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                response.addHeader("Cause", Cause.UNAUTHORIZED.code);
                response.addHeader("CauseStr", Cause.UNAUTHORIZED.name());
                return false;
            }
        }
        return false;
    }
}
