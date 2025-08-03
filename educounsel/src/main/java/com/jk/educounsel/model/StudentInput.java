package com.jk.educounsel.model;

public class StudentInput {
    private double percentile;
    private String department; // Branch name
    private String category;   // Category code
    private String region;
    private String location;
    private String capRound;
    private String quotaTag;
    private String homeUniversityStatus;
    private String minorityStatus;

    // Getters and Setters
    // ... (generate all getter/setter methods)


    public double getPercentile() {
        return percentile;
    }

    public void setPercentile(double percentile) {
        this.percentile = percentile;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCapRound() {
        return capRound;
    }

    public void setCapRound(String capRound) {
        this.capRound = capRound;
    }

    public String getQuotaTag() {
        return quotaTag;
    }

    public void setQuotaTag(String quotaTag) {
        this.quotaTag = quotaTag;
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
}
