package com.example.HeThongQuanLyTaiChinhThongMinh.repository;

import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; // cần thêm cái này
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // 🔹 Lấy category theo id + userId + chưa xóa
    @Query(
            "SELECT c " +
                    "FROM Category c " +
                    "WHERE c.id = :id " +
                    "AND c.user.id = :userId " +
                    "AND c.deletedAt IS NULL"
    )
    Optional<Category> findValidByIdAndUserId(
            @Param("id") Long id,
            @Param("userId") Long userId
    );


    // 🔹 Lấy tất cả category của user (chưa xóa)
    List<Category> findAllByUser_IdAndDeletedAtIsNull(Long userId);

    // 🔹 Lấy category của user, sắp xếp mới nhất trước
    List<Category> findAllByUserIdAndDeletedAtIsNullOrderByIdDesc(Long userId);
}

