package com.jk.educounsel.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Institute {

    @Id
    private String instituteCode;

    private String name;
    private String location; // lowercase 'l'
    private String region;

    //Relationship PK & FK
    @OneToMany(mappedBy = "institute", cascade = CascadeType.ALL)
    private List<InstituteBranch>branches;




    public String getInstituteCode() {
        return instituteCode;
    }

    public void setInstituteCode(String instituteCode) {
        this.instituteCode = instituteCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<InstituteBranch> getBranches() {
        return branches;
    }

    public void setBranches(List<InstituteBranch> branches) {
        this.branches = branches;
    }

    public String getInstituteName() {
        return this.name=name;
    }
}
