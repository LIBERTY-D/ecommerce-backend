package com.daniel.apps.ecommerce.app.service.email;


import java.util.Map;

public interface EmailService {

    void sendEmail(Map<String, Object> payload,String template);
}
