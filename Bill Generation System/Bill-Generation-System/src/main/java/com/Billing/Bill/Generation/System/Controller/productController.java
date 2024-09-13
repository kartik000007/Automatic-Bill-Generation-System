package com.Billing.Bill.Generation.System.Controller;

import com.Billing.Bill.Generation.System.Modules.Product;
import com.Billing.Bill.Generation.System.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RequestMapping("Add")
@RestController
public class productController {

    @Autowired
    private ProductService productService;


    @PostMapping("/AddAllProducts")
    public ResponseEntity<List<Product>> createProduct(@RequestBody List<Product> products) {
        // your code here
        return productService.saveProduct(products);
    }
}