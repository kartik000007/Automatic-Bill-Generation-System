package com.Billing.Bill.Generation.System.Scheduler;

import com.Billing.Bill.Generation.System.Service.EmailService;
import com.Billing.Bill.Generation.System.Service.ReportService;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;

@Component
public class ReportScheduler {

    @Autowired
    private ReportService reportService;

    @Autowired
    private EmailService emailService;

//    @Scheduled(cron = "0 0 0 * * *")
   @PostConstruct
    public void sendReport() throws IOException {
       try{
           reportService.generateReport();
           emailService.sendEmail();
           System.out.println("Report generated.........");
       }catch (Exception e){
           e.printStackTrace();
       }
//            emailService.sendEmail();
    }



}
