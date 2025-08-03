package com.jk.educounsel.repository;

import com.jk.educounsel.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    Page<Category> findByCategoryCodeContainingIgnoreCase(String keyword, Pageable pageable);
}
