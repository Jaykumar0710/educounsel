package com.jk.educounsel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Scholarship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String name;
    private String eligibleCategories;
    private double incomeLimit;
    private String documentRequired;
    private String applyLink;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEligibleCategories() {
        return eligibleCategories;
    }

    public void setEligibleCategories(String eligibleCategories) {
        this.eligibleCategories = eligibleCategories;
    }

    public double getIncomeLimit() {
        return incomeLimit;
    }

    public void setIncomeLimit(double incomeLimit) {
        this.incomeLimit = incomeLimit;
    }

    public String getDocumentRequired() {
        return documentRequired;
    }

    public void setDocumentRequired(String documentRequired) {
        this.documentRequired = documentRequired;
    }

    public String getApplyLink() {
        return applyLink;
    }

    public void setApplyLink(String applyLink) {
        this.applyLink = applyLink;
    }
}
