package com.jk.educounsel.service;

import com.jk.educounsel.model.Scholarship;
import com.jk.educounsel.repository.ScholarshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScholarshipService {
    @Autowired
    private ScholarshipRepository scholarshipRepository;

    public Page<Scholarship> getAllScholarships(Pageable pageable) {
        return scholarshipRepository.findAll(pageable);
    }

    public Page<Scholarship> searchScholarships(String keyword, Pageable pageable) {
        return scholarshipRepository.findByNameContainingIgnoreCaseOrEligibleCategoriesContainingIgnoreCase(
                keyword, keyword, pageable);
    }

    public Object countScholarships() {
        return scholarshipRepository.count();
    }
}







