package com.jk.educounsel.repository;

import com.jk.educounsel.model.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, String> {
    Page<Branch>findByBranchNameContainingIgnoreCase(String keyword, Pageable pageable);
}
