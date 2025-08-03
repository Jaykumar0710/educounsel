package com.jk.educounsel.service;

import com.jk.educounsel.model.Timeline;
import com.jk.educounsel.repository.TimelineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TimelineService {

    @Autowired
    private TimelineRepository timelineRepository;

    public List<TimelineService> getTimeLineByType(String type){
        return timelineRepository.findByType(type);
    }

    public Object countTimelines() {
        return timelineRepository.count();
    }

    public List<Timeline>getAllTimelines(){
     return timelineRepository.findAll();
    }

    public Optional<Timeline>getTimelineById(Long id){
        return  timelineRepository.findById(id);
    }

    public Page<Timeline>findByEventNameContainingIgnoreCase(String keyword, Pageable pageable){
        return  timelineRepository.findByeventnameContainingIgnoreCase(keyword, pageable);
    }


   public Page<Timeline>findAll(Pageable pageable){
        return  timelineRepository.findAll(pageable);
   }

   public void save(Timeline timeline){
        timelineRepository.save(timeline);
   }

   public  void deleteById(Long id){
        timelineRepository.deleteById(id);
   }

    public List<Timeline> findTop5ByOrderByDateDesc() {
        return timelineRepository.findAll();
    }
}
