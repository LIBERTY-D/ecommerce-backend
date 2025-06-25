package com.daniel.apps.ecommerce.app.service.email;


import com.daniel.apps.ecommerce.app.environment.MailEnv;
import com.daniel.apps.ecommerce.app.exception.EmailFailException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImp implements EmailService {
    public static final String UTF_8 = "UTF-8";
    public static final String EMAIL_FAILURE_MESSAGE = "EMAIL FAILURE";
    private  final JavaMailSender javaMailSender;
    private final MailEnv mailEnv;
    private final ITemplateEngine iTemplateEngine;


    @Async
    @Override
    public void sendEmail(Map<String, Object> payload, String template) {
            try{
                Context context =  new Context();
                context.setVariables(payload);

                String htmlContent =  iTemplateEngine.process(template,context);
                MimeMessage mimeMessage = getMimeMessage();
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true, UTF_8);
                mimeMessageHelper.setSubject((String) payload.get("subject"));
                mimeMessageHelper.setFrom(mailEnv.getUsername());
                mimeMessageHelper.setTo((String) payload.get("to"));
                mimeMessageHelper.setText(htmlContent, true);
                javaMailSender.send(mimeMessage);
            }catch (Exception exp){
                log.error(exp.getMessage());
                throw new EmailFailException(EMAIL_FAILURE_MESSAGE);

            }

    }

    private  MimeMessage getMimeMessage(){
        return  javaMailSender.createMimeMessage();
    }
}
