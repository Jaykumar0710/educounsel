package com.jk.educounsel.service;

import com.jk.educounsel.model.Cutoff;
import com.jk.educounsel.repository.CutoffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CutoffService {

    @Autowired
    private CutoffRepository cutoffRepository;


    public Object countCutoffs() {
        return cutoffRepository.count();
    }
}
