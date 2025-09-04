package com.example.HeThongQuanLyTaiChinhThongMinh.service.impl;

import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request.CategoryRequestDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response.CategoryResponseDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.Category;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.User;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.mapstruct.CategoryMapper;
import com.example.HeThongQuanLyTaiChinhThongMinh.repository.CategoryRepository;
import com.example.HeThongQuanLyTaiChinhThongMinh.service.CategoryService;
import com.example.HeThongQuanLyTaiChinhThongMinh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final UserService userService;

    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()
        -> new RuntimeException("not.found.category.id"+id));
    }

    @Override
    public String create(CategoryRequestDTO categoryRequestDTO) {
       try {
           User user = userService.getUserByUsername(
                   SecurityContextHolder.getContext().getAuthentication().getName()
           );
           Category category = categoryMapper.toCategory(categoryRequestDTO);
           category.setUser(user);
           categoryRepository.save(category);
           return "SUCCESS";
       } catch (RuntimeException e) {
           throw new RuntimeException("error.create.category."+ e);
       }
    }
    @Override
    public CategoryResponseDTO getCate(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(()
                -> new RuntimeException("not.found.category.id."+id));
        return categoryMapper.toCategoryResponseDTO(category);
    }

    @Override
    public List<CategoryResponseDTO> getAll() {
        User user = userService.getUserByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
        return categoryMapper.toListCategoryResponse(
                categoryRepository.findByUserId(user.getUserId())
        );
    }

    @Override
    public String deleteCate(Long id) {
        try {
            Category category = getById(id);
            category.setDeletedAt(LocalDate.now());
            categoryRepository.save(category);
            return "SUCCESS";
        } catch (RuntimeException e) {
            throw new RuntimeException("error.delete.category.id." + id + "." +e);
        }
    }
    @Override
    public String setCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
        try {
            Category category = getById(id);
            category.setName(categoryRequestDTO.getName());
            category.setDescription(categoryRequestDTO.getDescription());
            categoryRepository.save(category);
            return "SUCCESS";
        } catch (RuntimeException e) {
            throw new RuntimeException("error.set.category." + e);
        }
    }
}
