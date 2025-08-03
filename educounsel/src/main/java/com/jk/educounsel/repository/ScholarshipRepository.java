package com.jk.educounsel.repository;

import com.jk.educounsel.model.Scholarship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScholarshipRepository extends JpaRepository<Scholarship, Long> {
    Page<Scholarship> findByNameContainingIgnoreCaseOrEligibleCategoriesContainingIgnoreCase(
            String name, String category, Pageable pageable);
}

