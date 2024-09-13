package com.Billing.Bill.Generation.System.Repository;

import com.Billing.Bill.Generation.System.Modules.OderItemDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface OrderItemDTORepo extends JpaRepository<OderItemDTO,Integer> {
}
