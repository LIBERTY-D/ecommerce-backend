package com.daniel.apps.ecommerce.app.controller.imp;


import com.daniel.apps.ecommerce.app.controller.Controller;
import com.daniel.apps.ecommerce.app.dto.category.CategoryDto;
import com.daniel.apps.ecommerce.app.http.HttpResponse;
import com.daniel.apps.ecommerce.app.model.Category;
import com.daniel.apps.ecommerce.app.service.imp.CategoryServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class CategoryController extends  BaseController implements Controller<CategoryDto,Category,Long> {

    private  final CategoryServiceImp categoryServiceImp;

    @GetMapping("categories")
    @Override
    public ResponseEntity<HttpResponse<Collection<Category>>> findAll() {
        Collection<Category>  categories=
                categoryServiceImp.findAllCategories();

        HttpResponse<Collection<Category>> response =
                HttpResponse.<Collection<Category>>builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Categories retrieved successfully")
                        .data(categories).build();

        return ResponseEntity.ok(response);
    }


    @GetMapping("categories/{id}")
    @Override
    public ResponseEntity<HttpResponse<Category>> findOne(Long id) {
        Category  category= categoryServiceImp.findCategoryById(id);

        HttpResponse<Category> response =
                HttpResponse.<Category>builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Category retrieved successfully")
                        .data(category).build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("categories/{id}")
    @Override
    public ResponseEntity<HttpResponse<Category>> deleteOne(Long id) {
        categoryServiceImp.deleteCategory(id);
        HttpResponse<Category> response =
                HttpResponse.<Category>builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Category deleted successfully")
                        .build();

        return ResponseEntity.ok(response);
    }
    @PatchMapping("categories/{id}")
    @Override
    public ResponseEntity<HttpResponse<Category>> updateOne(Long id, CategoryDto updatedEntity) {
       Category category =  categoryServiceImp.updateCategory(id,updatedEntity);
        HttpResponse<Category> response =
                HttpResponse.<Category>builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Category updated successfully").
                         data(category)
                        .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("categories")
    @Override
    public ResponseEntity<HttpResponse<Category>> createOne(CategoryDto newEntity) {
        Category category =  categoryServiceImp.createCategory(newEntity);
        HttpResponse<Category> response =
                HttpResponse.<Category>builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Category created successfully").
                        data(category)
                        .build();

        return ResponseEntity.ok(response);
    }
}
