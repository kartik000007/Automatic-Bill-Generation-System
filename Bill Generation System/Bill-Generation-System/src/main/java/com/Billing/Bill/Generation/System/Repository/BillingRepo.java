package com.Billing.Bill.Generation.System.Repository;

import com.Billing.Bill.Generation.System.Modules.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingRepo extends JpaRepository<Bill,Integer> {
}
