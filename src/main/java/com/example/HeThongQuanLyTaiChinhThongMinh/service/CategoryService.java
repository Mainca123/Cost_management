package com.example.HeThongQuanLyTaiChinhThongMinh.service;


import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request.CategoryRequestDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response.CategoryResponseDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.Category;

import java.util.List;

public interface CategoryService {
    Category getById(Long id);
    String create(CategoryRequestDTO categoryRequestDTO);
    CategoryResponseDTO getCate(Long id);
    List<CategoryResponseDTO> getAll();
    String deleteCate(Long id);
    String setCategory(Long id, CategoryRequestDTO categoryRequestDTO);
}
