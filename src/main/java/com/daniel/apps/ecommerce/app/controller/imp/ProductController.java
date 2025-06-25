package com.daniel.apps.ecommerce.app.controller.imp;

import com.daniel.apps.ecommerce.app.controller.Controller;
import com.daniel.apps.ecommerce.app.dto.product.ProductDto;
import com.daniel.apps.ecommerce.app.http.HttpResponse;
import com.daniel.apps.ecommerce.app.model.Product;
import com.daniel.apps.ecommerce.app.service.imp.ProductServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class ProductController extends BaseController implements Controller<ProductDto,Product,Long> {

    private final ProductServiceImp productServiceImp;
    @GetMapping("products")
    @Override
    public ResponseEntity<HttpResponse<Collection<Product>>> findAll() {
        Collection<Product>  products = productServiceImp.findAllProducts();

        HttpResponse<Collection<Product>> response =
                HttpResponse.<Collection<Product>>builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Products retrieved successfully")
                .data(products).build();

        return ResponseEntity.ok(response);

    }

    @GetMapping("products/{id}")
    @Override
    public ResponseEntity<HttpResponse<Product>> findOne(Long id) {
        Product  product = productServiceImp.findProductById(id);
        HttpResponse<Product> response =
                HttpResponse.<Product>builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Product retrieved successfully")
                        .data(product).build();

     return  ResponseEntity.ok(response);
    }

    @DeleteMapping("products/{id}")

    @Override
    public ResponseEntity<HttpResponse<Product>> deleteOne(Long id) {
        this.productServiceImp.deleteProductById(id);
        HttpResponse<Product> product=  HttpResponse.<Product>builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Product deleted successfully")
                .build();
        return ResponseEntity.ok(product);
    }
    @PatchMapping("products/{id}")
    @Override
    public ResponseEntity<HttpResponse<Product>> updateOne(Long id, ProductDto updatedEntity) {
        Product updatedProduct = this.productServiceImp.updateProductById(id, updatedEntity);
        HttpResponse<Product> product=  HttpResponse.<Product>builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Product updated successfully").data(updatedProduct).
                build();
        return ResponseEntity.ok(product);
    }

    @PostMapping("products")
    @Override
    public ResponseEntity<HttpResponse<Product>> createOne(ProductDto newEntity) {

        Product createProduct =
                this.productServiceImp.createProductById(newEntity);
        HttpResponse<Product> product=  HttpResponse.<Product>builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Product created successfully").data(createProduct).
                build();
        return ResponseEntity.ok(product);

    }
}
