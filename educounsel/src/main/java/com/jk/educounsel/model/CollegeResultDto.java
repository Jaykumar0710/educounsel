package com.jk.educounsel.model;


public class CollegeResultDto {
    private String collegeName;
    private String location;
    private String region;
    private String branchName;
    private double closingPercentile;
    private String capRound;
    private String remarks;

    public CollegeResultDto(String collegeName, String location, String region,
                            String branchName, double closingPercentile,
                            String capRound, String remarks) {
        this.collegeName = collegeName;
        this.location = location;
        this.region = region;
        this.branchName = branchName;
        this.closingPercentile = closingPercentile;
        this.capRound = capRound;
        this.remarks = remarks;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
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

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public double getClosingPercentile() {
        return closingPercentile;
    }

    public void setClosingPercentile(double closingPercentile) {
        this.closingPercentile = closingPercentile;
    }

    public String getCapRound() {
        return capRound;
    }

    public void setCapRound(String capRound) {
        this.capRound = capRound;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}