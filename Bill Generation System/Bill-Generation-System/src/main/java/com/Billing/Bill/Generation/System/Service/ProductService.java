package com.Billing.Bill.Generation.System.Service;

import com.Billing.Bill.Generation.System.Modules.Product;
import com.Billing.Bill.Generation.System.Repository.productRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private productRepo productRepo;

    public ResponseEntity<List<Product>> saveProduct(List<Product> productsList) {
        try {
            productRepo.saveAll(productsList);
            return ResponseEntity.ok(productsList);
        } catch (Exception e) {
            return ResponseEntity.ok(null);
        }
    }
}

