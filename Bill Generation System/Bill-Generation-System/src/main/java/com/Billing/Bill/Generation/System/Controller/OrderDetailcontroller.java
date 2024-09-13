package com.Billing.Bill.Generation.System.Controller;

import com.Billing.Bill.Generation.System.Modules.OrderDetail;
import com.Billing.Bill.Generation.System.Modules.ResponseDTO;
import com.Billing.Bill.Generation.System.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orderDetail")
public class OrderDetailcontroller {


    @Autowired
    private OrderService orderService;

    @PostMapping("placeOrder")
    public ResponseDTO<OrderDetail> saveOrderDetail(@RequestBody OrderDetail orderDetail) {
        return orderService.placeOrder(orderDetail);
    }


}
