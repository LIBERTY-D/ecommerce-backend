package com.daniel.apps.ecommerce.app.service.imp;


import com.daniel.apps.ecommerce.app.dto.category.CategoryDto;
import com.daniel.apps.ecommerce.app.exception.NoSuchCategoryException;
import com.daniel.apps.ecommerce.app.model.Category;
import com.daniel.apps.ecommerce.app.model.Product;
import com.daniel.apps.ecommerce.app.repository.CategoryRepository;
import com.daniel.apps.ecommerce.app.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImp {
    private final CategoryRepository categoryRepository;
    private  final ProductRepository productRepository;
    public List<Category> findAllCategories(){
        log.info("[GETTING ALL CATEGORIES]");
        return  this.categoryRepository.findAll();
    }

    public Category  findCategoryById(Long id){
        log.info("[GET CATEGORY BY ID]");
        return  findById(id);
    }

    public  Category updateCategory(Long id, CategoryDto categoryDto){
        log.info("[UPDATING CATEGORY BY ID]");
        Category category = findById(id);
        category.setName(categoryDto.categoryName());
        return  categoryRepository.save(category);

    }
    public  Category createCategory(CategoryDto categoryDto){
        log.info("[CREATING CATEGORY]");
            Category category =  new Category(categoryDto.categoryName());
           return  categoryRepository.save(category);
    }

    @Transactional
    public  void deleteCategory(Long id){
        log.info("[DELETING  CATEGORY BY ID]");
        Category category = findById(id);
        List<Product> products = category.getProducts();
        if(!products.isEmpty()){
            for(Product p: products){
                p.setCategory(null);
            }
        }
        productRepository.saveAll(products);
        categoryRepository.deleteById(id);
    }




    private Category findById(Long id){
        Optional<Category> category =  categoryRepository.findById(id);
        if(category.isEmpty()){
            throw new NoSuchCategoryException("no category with such id");
        }
        return category.get();
    }

}
