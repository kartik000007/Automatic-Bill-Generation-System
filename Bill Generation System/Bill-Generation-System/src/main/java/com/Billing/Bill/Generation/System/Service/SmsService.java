package com.Billing.Bill.Generation.System.Service;

import com.Billing.Bill.Generation.System.Configuration.TwilioConfig;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsService {


    @Autowired
    TwilioConfig configuration;

    public void sendSms(String customerNumber, String message) {
        String customerPhoneNumber = "+91" + customerNumber;

        Message.creator(
                new PhoneNumber(customerPhoneNumber),
                new PhoneNumber(configuration.getTrialNumber()),
                message
        ).create();
        System.out.println("message sent to customer ID: " + customerPhoneNumber + " with message: " + message);

    }
}
