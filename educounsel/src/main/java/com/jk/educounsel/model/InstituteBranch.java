package com.jk.educounsel.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class InstituteBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  int intake;

    @ManyToOne
    @JoinColumn(name = "Institute_code")
    private Institute institute;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @OneToMany(mappedBy = "instituteBranch", cascade = CascadeType.ALL)
    private List<Cutoff>cutoffs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIntake() {
        return intake;
    }

    public void setIntake(int intake) {
        this.intake = intake;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public List<Cutoff> getCutoffs() {
        return cutoffs;
    }

    public void setCutoffs(List<Cutoff> cutoffs) {
        this.cutoffs = cutoffs;
    }
}
