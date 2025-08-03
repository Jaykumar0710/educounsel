package com.jk.educounsel.service;

import com.jk.educounsel.model.Branch;
import com.jk.educounsel.model.Institute;
import com.jk.educounsel.model.InstituteBranch;
import com.jk.educounsel.repository.InstituteBranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstituteBranchService {

    @Autowired
    private InstituteBranchRepository instituteBranchRepository;

    public List<InstituteBranch> getBranchesByInstitute(Institute institute){
        return  instituteBranchRepository.findByInstitute(institute);
    }

    public List<InstituteBranch>getInstiturtesByBranch(Branch branch){
        return instituteBranchRepository.findByBranch(branch);
    }
}
