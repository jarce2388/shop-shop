package com.test.shopservice.log;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AbstractAuditListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class SecurityEventLog extends AbstractAuditListener {

    @Override
    protected void onAuditEvent(AuditEvent event) {

        String message = event.getPrincipal() + " : " + event.getType();
        Logger logger = LogManager.getLogger("security-log");
        switch (event.getType()) {
            case "AUTHENTICATION_SUCCESS":
                message = message + ": Logueo Exitoso";
                logger.info(message);
                break;
            case "AUTHENTICATION_FAILURE":
                message = message + ": Error";
                logger.error(message);
                break;
            case "AUTHORIZATION_FAILURE":
                message = message + ": Error. Acceso denegado";
                logger.error(message);
                break;
            default:
                break;
        }
    }
}
