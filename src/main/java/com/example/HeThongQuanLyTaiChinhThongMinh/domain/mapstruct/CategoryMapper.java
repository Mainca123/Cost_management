package com.example.HeThongQuanLyTaiChinhThongMinh.domain.mapstruct;


import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response.CategoryResponseDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    List<CategoryResponseDTO> toListCategoryResponseDTO(List<Category> categoryList);
    CategoryResponseDTO toCategoryResponseDTO(Category category);
}
