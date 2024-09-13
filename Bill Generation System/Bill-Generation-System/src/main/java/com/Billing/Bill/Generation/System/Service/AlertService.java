package com.Billing.Bill.Generation.System.Service;

import com.Billing.Bill.Generation.System.Configuration.AdminConfi;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Struct;

@Service
public class AlertService {

    @Autowired
    SmsService smsService;
    @Autowired
    EmailService emailService;
    @Autowired
    WhatsAppService whatsAppService;
    @Autowired
    AdminConfi adminConfi;

    public void sendAlert(String productName,int inventory){
        String message = String.format("Alert: The inventory for product '%s' is below the threshold. " +
                "Remaining stock: %d", productName, inventory);

        // send sms message to admin
        smsService.sendSms(adminConfi.getAdminContactNumber(), message);

        // Send whatsApp message to admin
        whatsAppService.setWhatsAppMessage(adminConfi.getAdminContactNumber(), message);

        // send email to admin
        try{
            String subject= "Inventory Alert: Low Stock for " + productName;
            String emailTo= adminConfi.getAdminEmail();
            String emailFrom=adminConfi.getAdminEmail();
            emailService.sendEmailMessage(emailTo, emailFrom, message, subject);
        }catch(MessagingException e){
            e.printStackTrace();
        }
    }
}
