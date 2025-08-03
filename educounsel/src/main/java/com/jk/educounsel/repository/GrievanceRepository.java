package com.jk.educounsel.repository;

import com.jk.educounsel.model.Grievance;
import com.jk.educounsel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrievanceRepository extends JpaRepository<Grievance, Long> {
    List<Grievance> findByUser(User user);
    long countByStatus(String status);


    List<Grievance> findTop5ByOrderByIdDesc();

}

