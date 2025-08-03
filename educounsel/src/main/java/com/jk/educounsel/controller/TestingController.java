package com.jk.educounsel.controller;

import com.jk.educounsel.model.Branch;
import com.jk.educounsel.model.Category;
import com.jk.educounsel.model.Timeline;
import com.jk.educounsel.service.BranchService;
import com.jk.educounsel.service.CategoryService;
import com.jk.educounsel.service.TimelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

public class TestingController {
    @Autowired
    BranchService branchService;
    //Pagination for branch
//    @GetMapping("/branches")
//    public String listBranches(@RequestParam(value = "keyword", required = false)String keyword,
//                               @RequestParam(value = "page",defaultValue = "0") int page, Model model){
//
//        Pageable pageable = PageRequest.of(page, 10);
//        Page<Branch> branchePage;
//
//        if (keyword != null && !keyword.trim().isEmpty()){
//            branchePage=branchService.findByBranchNameContainingIgnoreCase(keyword,pageable);
//        }else {
//            branchePage=branchService.findAll(pageable);
//        }
//
//        model.addAttribute("branches", branchePage.getContent());
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages",branchePage.getTotalPages());
//        model.addAttribute("keyword",keyword);
//
//        return "branch-list";
//    }
//
//    //Branch Frorm for adding new BranchFrom
//    @GetMapping("/branches/new")
//    public String newBranchesForms(Model model){
//        model.addAttribute("branch", new Branch());
//        return "branch-form";
//    }
//
//    //Saving New Branch
//    @PostMapping("/branches/save")
//    public String saveBranch(@ModelAttribute Branch branch){
//        branchService.save(branch);
//        return "redirect:/admin/branches";
//    }
//
//    //edit Branches
//    @GetMapping("/branches/edit/{id}")
//    public String editBranch(@PathVariable("code") String code, Model model){
//        Optional<Branch>optionalBranch = branchService.getById(code);
//        if (optionalBranch.isPresent()){
//            model.addAttribute("branch", optionalBranch.get());
//        }else {
//            return "redirect:/admin/branches";
//        }
//        return "branch-form";
//    }
//
//    //Delete Branch
//
//    @GetMapping("/branches/delete/{id}")
//    public String deleteBranch(@PathVariable("code") String code){
//        branchService.deleteById(code);
//        return "redirect:/admin/branches";
//    }
//
    @Autowired
    private TimelineService timelineService;

    @GetMapping("/timelines")
    public String listTimeleines(@RequestParam(value = "keyword", required = false) String keyword,
                                 @RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Timeline> timelinePage;
        if (keyword != null && !keyword.trim().isEmpty()) {
            timelinePage = timelineService.findByEventNameContainingIgnoreCase(keyword, pageable);

        } else {
            timelinePage = timelineService.findAll(pageable);
        }
        model.addAttribute("timelines", timelinePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", timelinePage.getTotalPages());
        model.addAttribute("keyword", keyword);

        return "timeline-list";
    }
    //Add new Timeleines
    @GetMapping("/timelines/new")
    public String timelineForm(Model model){
        model.addAttribute("timeline", new Timeline());
        return "timeline-form";
    }

    //Save Neew TimeLine
    @PostMapping("/timelines/save")
    public String saveTimelines(@ModelAttribute Timeline timeline){
        timelineService.save(timeline);
        return "redirect:/admin/timelines";
    }

    //Edit Timelines


    //Delete Timeline
    @GetMapping("/timelines/delete/{id}")
    public String deleteTimeline(@PathVariable("id") Long code){
        timelineService.deleteById(code);
        return "redirect:/admin/timelines";

    }

    @GetMapping("/timelines/edit/{id}")
        public String editTimeline(@PathVariable("id") Long code, Model model){
            Optional<Timeline>optionalTimeline =timelineService.getTimelineById(code);
            if (optionalTimeline.isPresent()){
                model.addAttribute("branch", optionalTimeline.get());
            }else {
                return"redirect:/admin/timelines";
            }
            return "timeline-form";
        }
    }





