package com.coding.core.event;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEvents {
    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failures) {
       //TODO
    }
}
