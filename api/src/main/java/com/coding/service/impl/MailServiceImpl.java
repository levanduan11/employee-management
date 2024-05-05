package com.coding.service.impl;

import com.coding.domain.SendMail;
import com.coding.service.MailService;
import com.google.common.base.Preconditions;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;


import java.util.Locale;
import java.util.Map;

import static com.coding.core.constant.MessageKeyConstant.MAIL_ACTIVATION_TITLE_KEY;
import static com.coding.core.constant.TemplateKeyConstant.MAIL_USER_ACTIVATION;
import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class MailServiceImpl implements MailService {
    private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

    private final JavaMailSender javaMailSender;
    private final MessageSource messageSource;
    private final SpringTemplateEngine templateEngine;

    public MailServiceImpl(JavaMailSender javaMailSender, MessageSource messageSource, SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }
    @Async
    @Override
    public void sendMail(SendMail sendMail) {
        Preconditions.checkArgument(sendMail != null, "sendMail must not be null");
        log.debug("send mail to: {}, subject: {}, content: {}", sendMail.getTo(), sendMail.getSubject(), sendMail.getContent());
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, sendMail.isMultipart(), UTF_8.name());
            message.setTo(sendMail.getTo());
            message.setCc(sendMail.getCc());
            message.setBcc(sendMail.getBcc());
            message.setSubject(sendMail.getSubject());
            message.setText(sendMail.getContent(), sendMail.isHtml());
            javaMailSender.send(mimeMessage);
            log.debug("Send mail success");
        } catch (MessagingException e) {
            log.warn("Send mail failed", e);
        }
    }
    @Async
    public void sendMail(SendMail sendMail, String templateName, Map<String, Object> variables, String titleKey) {
        Preconditions.checkArgument(sendMail != null, "sendMail must not be null");
        Locale locale = Locale.getDefault();
        Context context = new Context(locale);
        context.setVariables(variables);
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendMail.setContent(content);
        sendMail.setSubject(subject);
        sendMail(sendMail);
    }
    @Async
    public void sendActivationMail(SendMail sendMail, Map<String, Object> variables) {
        Preconditions.checkArgument(sendMail != null, "sendMail must not be null");
       sendMail(sendMail, MAIL_USER_ACTIVATION, variables, MAIL_ACTIVATION_TITLE_KEY);
    }
}
