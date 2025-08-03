package com.jk.educounsel.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Branch {
    @Id
       private String branchId;

    private String branchName;

    //Relationship
    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private List<InstituteBranch> instituteBranches;

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public List<InstituteBranch> getInstituteBranches() {
        return instituteBranches;
    }

    public void setInstituteBranches(List<InstituteBranch> instituteBranches) {
        this.instituteBranches = instituteBranches;
    }
}
