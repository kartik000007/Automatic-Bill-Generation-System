package com.Billing.Bill.Generation.System.Repository;

import com.Billing.Bill.Generation.System.Modules.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface OrderDeetailRepo extends JpaRepository<OrderDetail,Integer> {
}
