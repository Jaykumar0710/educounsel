package com.jk.educounsel.service;

import com.jk.educounsel.model.Branch;
import com.jk.educounsel.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;

    public List<Branch>getAllBranches(){
        return branchRepository.findAll();
    }

    public Optional<Branch> getBranchById(String id){
        return branchRepository.findById(id);
    }
    public void saveBranch(Branch branch){
        branchRepository.save(branch);
    }

    public Page<Branch>findByBranchNameContainingIgnoreCase(String keyword, Pageable pageable){
        return branchRepository.findByBranchNameContainingIgnoreCase(keyword, pageable);
    }


    public Page<Branch>findAll(Pageable pageable){
        return   branchRepository.findAll(pageable);
    }

    public void save(Branch branch){
        branchRepository.save(branch);
    }

    public Optional<Branch>getById(String code){
        return branchRepository.findById(code);
    }

    public void deleteById(String id){
        branchRepository.deleteById(id);
    }

    public Object countBranches() {
        return  branchRepository.count();
    }
}
