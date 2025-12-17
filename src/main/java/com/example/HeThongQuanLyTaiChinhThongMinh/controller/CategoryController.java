package com.example.HeThongQuanLyTaiChinhThongMinh.controller;

import com.example.HeThongQuanLyTaiChinhThongMinh.base.RestApiV1;
import com.example.HeThongQuanLyTaiChinhThongMinh.base.VsResponseUtil;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request.CategoryRequestDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/user/categories/{id}")
    public ResponseEntity<?> getCategory(@PathVariable Long id){
        return VsResponseUtil.success(categoryService.getCate(id));
    }

    @GetMapping("/user/categories")
    public ResponseEntity<?> getAllCategory(){
        return VsResponseUtil.success(categoryService.getAll());
    }

    @PutMapping("/user/categories/{id}")
    public ResponseEntity<?> setCategory(
            @PathVariable Long id,
            @RequestBody CategoryRequestDTO categoryRequestDTO
    ){
        return VsResponseUtil.success(categoryService.setCategory(id, categoryRequestDTO));
    }

    @PostMapping("/user/categories")
    public ResponseEntity<?> createCategory(
            @RequestBody CategoryRequestDTO categoryRequestDTO
    ){
        return VsResponseUtil.success(categoryService.create(categoryRequestDTO));
    }

    @DeleteMapping("/user/categories/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return VsResponseUtil.success(categoryService.deleteCate(id));
    }
}
