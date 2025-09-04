package com.example.HeThongQuanLyTaiChinhThongMinh.domain.mapstruct;

import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request.CategoryRequestDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response.CategoryResponseDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequestDTO categoryRequestDTO);
    CategoryResponseDTO toCategoryResponseDTO(Category category);
    List<CategoryResponseDTO> toListCategoryResponse(List<Category> categoryList);
}
