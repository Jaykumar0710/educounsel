package com.jk.educounsel.service;


import com.jk.educounsel.model.CollegeResultDto;
import com.jk.educounsel.model.Institute;
import com.jk.educounsel.repository.CutoffRepository;
import com.jk.educounsel.repository.InstituteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InstituteService {



    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private InstituteRepository instituteRepository;




    public Page<Institute> findByNameContainingIgnoreCase(String keyword, Pageable pageable) {
        return instituteRepository.findByNameContainingIgnoreCase(keyword, pageable);
    }

    public Page<Institute> findAll(Pageable pageable) {
        return instituteRepository.findAll(pageable);
    }

    public Optional<Institute> getById(String code) {
        return instituteRepository.findById(code);
    }

    public void save(Institute institute) {
        instituteRepository.save(institute);

    }

    public void deleteById(String code) {
        instituteRepository.deleteById(code);
    }

    public Object getAllInstitutes() {
       return instituteRepository.findAll();
    }


    public Object countInstitutes() {
        return instituteRepository.count();
    }
}



