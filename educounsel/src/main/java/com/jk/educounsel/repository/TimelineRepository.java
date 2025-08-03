package com.jk.educounsel.repository;

import com.jk.educounsel.model.Timeline;
import com.jk.educounsel.service.TimelineService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TimelineRepository extends JpaRepository<Timeline, Long> {
    List<TimelineService> findByType(String type);

    Page<Timeline>findByeventnameContainingIgnoreCase(String keyword, Pageable pageable);
}
