package com.coding.service;

import com.coding.domain.SendMail;

import java.util.Map;

public interface MailService {
    void sendMail(SendMail sendMail);

    void sendMail(SendMail sendMail, String templateName, Map<String, Object> variables, String titleKey);

    void sendActivationMail(SendMail sendMail, Map<String, Object> variables);
}
