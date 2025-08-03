package com.jk.educounsel.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Category {
    @Id
    private String categoryCode;

    private  String categoryName;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Cutoff>cutoffs;

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    public List<Cutoff> getCutoffs() {
        return cutoffs;
    }

    public void setCutoffs(List<Cutoff> cutoffs) {
        this.cutoffs = cutoffs;
    }
}
