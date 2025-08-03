package com.jk.educounsel.repository;

import com.jk.educounsel.model.CollegeResultDto;
import com.jk.educounsel.model.Institute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstituteRepository extends JpaRepository<Institute, String> {
    List<Institute> findByRegion(String region);

    Page<Institute> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    @Query("SELECT DISTINCT i.region FROM Institute i")
    List<String> findDistinctRegions();

    @Query(value = """
        SELECT 
            i.institute_code AS instituteCode,
            i.name AS collegeName,
            i.location,
            i.region,
            b.branch_id AS branchId,
            b.branch_name AS branchName,
            ib.intake,
            c.cap_round AS capRound,
            c.quota_tag AS quotaTag,
            c.closing_percentile AS closingPercentile,
            c.remarks,
            cat.category_code AS categoryCode,
            cat.description AS categoryDescription
        FROM 
            cutoff c
        JOIN 
            institute_branch ib ON c.institute_branch_id = ib.id
        JOIN 
            branch b ON ib.branch_id = b.branch_id
        JOIN 
            institute i ON ib.institute_code = i.institute_code
        JOIN 
            category cat ON c.category_code = cat.category_code
        WHERE 
            c.closing_percentile <= :percentile
            AND c.cap_round = :capRound
            AND c.quota_tag = :quotaTag
            AND c.minority_status = :minorityStatus
            AND c.home_university_status = :homeUniversityStatus
            AND c.category_code = :categoryCode
        ORDER BY 
            c.closing_percentile DESC
        LIMIT 10
    """, nativeQuery = true)
    List<CollegeResultDto> findTopColleges(
            @Param("percentile") Double percentile,
            @Param("capRound") String capRound,
            @Param("quotaTag") String quotaTag,
            @Param("minorityStatus") String minorityStatus,
            @Param("homeUniversityStatus") String homeUniversityStatus,
            @Param("categoryCode") String categoryCode
    );


}
