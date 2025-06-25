package com.daniel.apps.ecommerce.app.service.imp;


import com.daniel.apps.ecommerce.app.dto.product.ProductDto;
import com.daniel.apps.ecommerce.app.exception.NoSuchProductException;
import com.daniel.apps.ecommerce.app.model.Category;
import com.daniel.apps.ecommerce.app.model.Product;
import com.daniel.apps.ecommerce.app.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Setter
@Slf4j
public class ProductServiceImp {

    private  final ProductRepository productRepository;
    private  final  CategoryServiceImp categoryServiceImp;


    public Collection<Product> findAllProducts(){
        log.info("[FIND ALL PRODUCTS]");
        return this.productRepository.findAllWithCategory();

    }

    public Product findProductById(Long id){
        log.info("[FIND PRODUCT BY ID]");
         return  this.findById(id);
    }
    public  void deleteProductById(Long id){
        log.info("[DELETE PRODUCT BY ID]");
        this.findById(id);
        this.productRepository.deleteById(id);
    }

    @Transactional
    public  Product updateProductById(Long id, ProductDto productDto){
        log.info("[UPDATE PRODUCT BY ID]");
        Product updatedProduct  =this.findById(id);

        updatedProduct.setId(id);
        updatedProduct.setDescription(productDto.description());
        updatedProduct.setName(productDto.name());
        updatedProduct.setQuantity(productDto.quantity());
        Category category = categoryServiceImp.findCategoryById(productDto.categoryId());
        updatedProduct.setCategory(category);
        return  this.productRepository.save(updatedProduct);

    }
    public  Product createProductById(ProductDto productDto){
        log.info("[CREATING PRODUCT]");
        Product product =  new Product();
        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setPrice(productDto.price());
        product.setQuantity(productDto.quantity());
        Category category = categoryServiceImp.findCategoryById(productDto.categoryId());
        product.setCategory(category);

        return  productRepository.save(product);


    }



    private Product findById(Long id){
        Optional<Product> productOpt = productRepository.findByIdWithCategory(id);
        if(productOpt.isEmpty()){
            throw new NoSuchProductException("no product with such id");
        }
        return productOpt.get();
    }




}
