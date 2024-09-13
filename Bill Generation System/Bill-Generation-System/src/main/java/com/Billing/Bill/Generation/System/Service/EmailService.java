package com.Billing.Bill.Generation.System.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail() throws MessagingException {
        MimeMessage mimeMailMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(mimeMailMessage,true);

        String date=new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String filePath="src/main/resources/Reports/products_report_" + date + ".csv";

        helper.setFrom("vaghasiyakartik2950@gmail.com");
        helper.setTo("vaghasiyakartik2950@gmail.com");
        helper.setSubject("Daily Product and Inventory Report - " + date);
        helper.setText("Please find the attached daily report of products and inventory for " + date + ".");

        File file = new File(filePath);
        helper.addAttachment(file.getName(), file);

        javaMailSender.send(mimeMailMessage);

        System.out.println("sendMail.......");

    }

    public void sendEmailMessage(String emailTo,String emailFrom,String message,String subject) throws MessagingException {
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);

        helper.setFrom(emailFrom);
        helper.setTo(emailTo);
        helper.setSubject(subject);
        helper.setText(message);

        javaMailSender.send(mimeMessage);

        System.out.println("sendEmailMessage.......");
    }
}
