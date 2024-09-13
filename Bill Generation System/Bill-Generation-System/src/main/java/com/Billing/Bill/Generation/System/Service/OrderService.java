package com.Billing.Bill.Generation.System.Service;

import com.Billing.Bill.Generation.System.Modules.*;
import com.Billing.Bill.Generation.System.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Random;

@Service
public class OrderService {
    private static final int INVENTORY_THRESHOLD = 10;
    @Autowired
    OrderDeetailRepo orderDetailRepository;
    @Autowired
    CustomerRepo customerRepository;
    @Autowired
    BillingRepo billRepository;
    @Autowired
    SmsService smsService;
    @Autowired
    WhatsAppService whatsAppService;
    @Autowired
    AlertService alertService;
    @Autowired
    private OrderItemDTORepo orderItemRepository;
    @Autowired
    private productRepo productRepo;

    public ResponseDTO<OrderDetail> placeOrder(OrderDetail orderDetail) {
        try {
            for (OderItemDTO orderItem : orderDetail.getOrderItems()) {
                Product product = productRepo.findById(orderItem.getProductId()).orElseThrow(() -> new RuntimeException("Product not found with ID: " + orderItem.getProductId()));

                if (product.getInventory() < orderItem.getQuantity()) {
                    return new ResponseDTO<>(null, HttpStatus.BAD_REQUEST, "Insufficient inventory for product ID: " + orderItem.getProductId());
                }
            }
            Customer customer = customerRepository.findById(orderDetail.getCustomer_id()).orElseThrow(() -> new RuntimeException("Customer not found :" + orderDetail.getCustomer_id()));


            boolean paymentSuccess = processPayment();
            if (!paymentSuccess) {
                String paymentFailed = String.format(
                        " Hello %s! Unfortunately, your order could not be processed due to a payment failure. Please try again or contact support for assistance. We're here to help! ",
                        orderDetail.getOrderId()
                );
//
                smsService.sendSms(String.valueOf(customer.getMobileNumber()), paymentFailed);
                whatsAppService.setWhatsAppMessage(String.valueOf(customer.getMobileNumber()), paymentFailed);
                return new ResponseDTO<>(null, HttpStatus.PAYMENT_REQUIRED, "payment failed");
            }

            OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);
            orderItemRepository.saveAll(orderDetail.getOrderItems());

            for (OderItemDTO orderItem : savedOrderDetail.getOrderItems()) {
                Product product = productRepo.findById(orderItem.getProductId()).orElseThrow(() -> new RuntimeException("Product not found with ID: " + orderItem.getProductId()));

                product.setInventory(product.getInventory() - orderItem.getQuantity());
                productRepo.save(product);
                if (product.getInventory() < INVENTORY_THRESHOLD) {
                    alertService.sendAlert(product.getName(), product.getInventory());
                }
            }
            Bill bill = generateBill(savedOrderDetail);
            billRepository.save(bill);

            String message = "Dear " + customer.getName() + ", your order (ID: " + orderDetail.getOrderId() + ") has been placed successfully. Total amount: " + bill.getAmount();
//            String message = String.format(
//                    "Hello! Your order with ID %s has been successfully placed. Your total payment is $%.2f. Thank you for choosing us. We'll notify you once it's on its way.",
//                    orderDetail.getOrderId(),
//                    bill.getAmount());

            smsService.sendSms(String.valueOf(customer.getMobileNumber()), message);
            whatsAppService.setWhatsAppMessage(String.valueOf(customer.getMobileNumber()), message);


            return new ResponseDTO<>(savedOrderDetail, HttpStatus.OK, "Order placed successfully");
        } catch (Exception e) {
            return new ResponseDTO<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "failed to place");
        }
    }

    //Payment method for order

    private boolean processPayment() {
        Random random = new Random();
        return random.nextInt(4) != 0;
    }

    private Bill generateBill(OrderDetail orderDetail) {
        double totalAmount = 0.0;

        for (OderItemDTO item : orderDetail.getOrderItems()) {
            Product product = productRepo.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with ID: " + item.getProductId()));

            double productPrice = product.getPrice();
            double gstRate = product.getGST();
            double itemTotal = item.getQuantity() * productPrice;

            double gstAmount = itemTotal * (gstRate / 100);
            totalAmount += itemTotal + gstAmount;
        }


        Bill bill = new Bill();
        bill.setOrderId(orderDetail.getOrderId());
        bill.setDate(LocalDate.now());
        bill.setAmount(totalAmount);
        bill.setCustomerId(orderDetail.getCustomer_id());


        Customer customer = customerRepository.findById(orderDetail.getCustomer_id())
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + orderDetail.getCustomer_id()));
        bill.setCustomerName(customer.getName());
        bill.setCustomerEmail(customer.getEmail());
        bill.setCustomerMobileNumber(customer.getMobileNumber());
        return bill;
    }

    private double getProductPrice(long productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
        return product.getPrice();
    }
}
