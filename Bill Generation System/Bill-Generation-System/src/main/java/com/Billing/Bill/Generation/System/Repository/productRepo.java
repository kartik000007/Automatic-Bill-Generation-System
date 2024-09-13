package com.Billing.Bill.Generation.System.Repository;

import com.Billing.Bill.Generation.System.Modules.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface productRepo extends JpaRepository<Product,Long> {

}
