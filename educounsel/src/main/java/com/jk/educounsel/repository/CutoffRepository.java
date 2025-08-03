package com.jk.educounsel.repository;


import com.jk.educounsel.model.Cutoff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CutoffRepository extends JpaRepository<Cutoff, Long> {

    @Query("SELECT c FROM Cutoff c " +
            "JOIN c.instituteBranch ib " +
            "JOIN ib.branch b " +
            "JOIN ib.institute i " +
            "JOIN c.category cat " +
            "WHERE cat.categoryCode = :categoryCode " +
            "AND c.closingPercentile BETWEEN :minPercentile AND :maxPercentile " +
            "AND (:department IS NULL OR LOWER(b.branchName) LIKE LOWER(CONCAT('%', :department, '%'))) " +
            "AND (:region IS NULL OR LOWER(i.region) LIKE LOWER(CONCAT('%', :region, '%'))) " +
            "AND (:location IS NULL OR LOWER(i.location) LIKE LOWER(CONCAT('%', :location, '%'))) " +
            "AND (:capRound IS NULL OR LOWER(c.capRound) = LOWER(:capRound)) " +
            "OR (:quotaTag IS NULL OR LOWER(c.quotaTag) = LOWER(:quotaTag)) " +
            "AND (:homeUniversityStatus IS NULL OR LOWER(c.homeUniversityStatus) = LOWER(:homeUniversityStatus)) " +
            "OR (:minorityStatus IS NULL OR LOWER(c.minorityStatus) = LOWER(:minorityStatus))")
    List<Cutoff> findMatchingCutoffsFlexible(
            @Param("categoryCode") String categoryCode,
            @Param("minPercentile") double min,
            @Param("maxPercentile") double max,
            @Param("department") String department,
            @Param("region") String region,
            @Param("location") String location,
            @Param("capRound") String capRound,
            @Param("quotaTag") String quotaTag,
            @Param("homeUniversityStatus") String homeUniversityStatus,
            @Param("minorityStatus") String minorityStatus
    );

    @Query("SELECT c FROM Cutoff c " +
            "JOIN c.instituteBranch ib " +
            "JOIN ib.branch b " +
            "JOIN ib.institute i " +
            "JOIN c.category cat " +
            "WHERE cat.categoryCode = :categoryCode " +
            "AND c.closingPercentile BETWEEN :minPercentile AND :maxPercentile " +
            "AND (:department IS NULL OR LOWER(b.branchName) LIKE LOWER(CONCAT('%', :department, '%'))) " +
            "AND (:region IS NULL OR LOWER(i.region) LIKE LOWER(CONCAT('%', :region, '%'))) " +
            "AND (:capRound IS NULL OR LOWER(c.capRound) = LOWER(:capRound))")
    List<Cutoff> findLikelyCutoffsFlexible(
            @Param("department") String department,
            @Param("categoryCode") String categoryCode,
            @Param("minPercentile") double minPercentile,
            @Param("maxPercentile") double maxPercentile,
            @Param("region") String region,
            @Param("capRound") String capRound
    );



    // In CutoffRepository
    @Query("SELECT DISTINCT c.capRound FROM Cutoff c")
    List<String> findDistinctCapRounds();

    @Query("SELECT DISTINCT c.quotaTag FROM Cutoff c WHERE c.quotaTag IS NOT NULL")
    List<String> findDistinctQuotaTags();
}

