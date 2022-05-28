package com.test.shopservice.config;

import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.security.AbstractAuthenticationAuditListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AuthenticationEventConfig extends AbstractAuthenticationAuditListener {

    public static final String AUTHENTICATION_FAILURE = "AUTHENTICATION_FAILURE";
    public static final String AUTHENTICATION_SUCCESS = "AUTHENTICATION_SUCCESS";

    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent  event) {

        if (event instanceof AuthenticationSuccessEvent) {
            onAuthenticationSuccessEvent((AuthenticationSuccessEvent) event);
        }
        else if(event instanceof AbstractAuthenticationFailureEvent){
            onAuthenticationFailureEvent((AbstractAuthenticationFailureEvent) event);
        }
        else  if(event instanceof AuthenticationFailureBadCredentialsEvent){
            onAuthenticationFailureEvent((AuthenticationFailureBadCredentialsEvent) event);
        }
    }

    private void onAuthenticationSuccessEvent(AuthenticationSuccessEvent event) {

        Map<String, Object> data = new HashMap<>();
        if (event.getAuthentication().getDetails() != null) {
            data.put("details", event.getAuthentication().getDetails());
        }
        publish(new AuditEvent(event.getAuthentication().getName(), AUTHENTICATION_SUCCESS, data));
    }

    private void onAuthenticationFailureEvent(AbstractAuthenticationFailureEvent event) {

        Map<String, Object> data = new HashMap<>();
        data.put("type", event.getException().getClass().getName());
        data.put("message", event.getException().getMessage());
        if (event.getAuthentication().getDetails() != null) {
            data.put("details", event.getAuthentication().getDetails());
        }
        publish(new AuditEvent(event.getAuthentication().getName(), AUTHENTICATION_FAILURE, data));
    }
}
