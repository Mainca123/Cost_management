package com.example.HeThongQuanLyTaiChinhThongMinh.repository;

import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; // cần thêm cái này
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.user.userId = :id AND c.deletedAt IS NULL")
    List<Category> findByUserId(@Param("id") Long id);
}
