package com.jk.educounsel.repository;

import com.jk.educounsel.model.Branch;
import com.jk.educounsel.model.Institute;
import com.jk.educounsel.model.InstituteBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstituteBranchRepository extends JpaRepository<InstituteBranch, Long> {
    List<InstituteBranch> findByInstitute(Institute institute);
    List<InstituteBranch> findByBranch(Branch branch);
}
