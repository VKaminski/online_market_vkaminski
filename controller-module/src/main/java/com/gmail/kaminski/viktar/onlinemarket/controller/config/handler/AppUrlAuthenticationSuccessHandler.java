package com.gmail.kaminski.viktar.onlinemarket.controller.config.handler;

import com.gmail.kaminski.viktar.onlinemarket.controller.config.GlobalValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

@Component
public class AppUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private static Logger logger = LoggerFactory.getLogger(LoginAccessDeniedHandler.class);
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Autowired
    private GlobalValue globalValue;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    protected void handle(HttpServletRequest request,
                          HttpServletResponse response,
                          Authentication authentication)
            throws IOException {

        String targetUrl = determineTargetUrl(authentication);
        if (response.isCommitted()) {
            logger.debug(
                    "Response has already been committed. Unable to redirect to "
                            + targetUrl);
            return;
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    protected String determineTargetUrl(Authentication authentication) {
        boolean isAdministrator = false;
        boolean isCustomer = false;
        Collection<? extends GrantedAuthority> authorities
                = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_" + globalValue.getAdministratorRoleName())) {
                isAdministrator = true;
                break;
            }
            if (grantedAuthority.getAuthority().equals("ROLE_" + globalValue.getCustomerRoleName())) {
                isCustomer = true;
                break;
            }
        }
        if (isAdministrator) {
            return "/users";
        }else if (isCustomer){
            return "/articles";
        } else {
            return "/login";
        }
    }

}
