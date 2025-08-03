package com.jk.educounsel.model;

import jakarta.persistence.*;

@Entity
public class Cutoff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CutoffId;

    private  String capRound;
    private String homeUniversityStatus;
    private String minorityStatus;
    private String quotaTag;
    private double closingPercentile;
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "institute_branch_id")
    private InstituteBranch instituteBranch;

    @ManyToOne
    @JoinColumn(name = "category_code")
    private  Category category;

    public Long getCutoffId() {
        return CutoffId;
    }

    public void setCutoffId(Long cutoffId) {
        CutoffId = cutoffId;
    }

    public String getCapRound() {
        return capRound;
    }

    public void setCapRound(String capRound) {
        this.capRound = capRound;
    }

    public String getHomeUniversityStatus() {
        return homeUniversityStatus;
    }

    public void setHomeUniversityStatus(String homeUniversityStatus) {
        this.homeUniversityStatus = homeUniversityStatus;
    }

    public String getMinorityStatus() {
        return minorityStatus;
    }

    public void setMinorityStatus(String minorityStatus) {
        this.minorityStatus = minorityStatus;
    }

    public String getQuotaTag() {
        return quotaTag;
    }

    public void setQuotaTag(String quotaTag) {
        this.quotaTag = quotaTag;
    }

    public double getClosingPercentile() {
        return closingPercentile;
    }

    public void setClosingPercentile(double closingPercentile) {
        this.closingPercentile = closingPercentile;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public InstituteBranch getInstituteBranch() {
        return instituteBranch;
    }

    public void setInstituteBranch(InstituteBranch instituteBranch) {
        this.instituteBranch = instituteBranch;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
