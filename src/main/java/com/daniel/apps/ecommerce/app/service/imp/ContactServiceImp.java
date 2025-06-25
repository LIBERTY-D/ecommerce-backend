package com.daniel.apps.ecommerce.app.service.imp;


import com.daniel.apps.ecommerce.app.dto.contact.ContactDto;
import com.daniel.apps.ecommerce.app.service.email.EmailService;
import com.daniel.apps.ecommerce.app.util.MailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactServiceImp {
    private  final EmailService emailService;

    public void ContactUs(ContactDto contactDto){

        emailService.sendEmail(MailUtil.contactPayload(contactDto.getEmail(), contactDto.getFullName()),"contact" +
                "-us.html");

    }
}
