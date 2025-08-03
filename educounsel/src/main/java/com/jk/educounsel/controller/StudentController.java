package com.jk.educounsel.controller;



import com.jk.educounsel.model.*;
import com.jk.educounsel.repository.BranchRepository;
import com.jk.educounsel.repository.CategoryRepository;
import com.jk.educounsel.repository.CutoffRepository;
import com.jk.educounsel.repository.InstituteRepository;
import com.jk.educounsel.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private UserService userService;

    @Autowired
    private InstituteRepository instituteRepository;

    @Autowired
    private CutoffRepository cutoffRepository;

    @Autowired
    private InstituteService instituteService;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PredictionService predictionService;

    @Autowired
    private ScholarshipService scholarshipService;

    @Autowired
    private  TimelineService timelineService;

    @Autowired
    private  NotificationService notificationService;

    @Autowired
    private  GrievanceService grievanceService;

    private  final String uploadGrievanceDir = "grievance/";



    // ----------------------
    // STUDENT DASHBOARD
    // ----------------------
    @GetMapping("/dashboard")
    public String showDashboard(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("timelineCount", timelineService.countTimelines());
        model.addAttribute("notifications", notificationService.getAllNotifications());

        List<Timeline> latestTimelines = timelineService.findTop5ByOrderByDateDesc();
        model.addAttribute("latestTimelines", latestTimelines);
        return "student-dashboard";
    }

    // ----------------------
    // PREDICT COLLEGE FORM PAGE
    // ----------------------
//    @GetMapping("/predict")
//    public String showPredictPage(Model model) {
//        model.addAttribute("branches", branchRepository.findAll());
//        model.addAttribute("categories", categoryRepository.findAll());
//        model.addAttribute("studentInput", new StudentInput());
//        return "predict-college";
//    }

//    @GetMapping("/predict")
//    public String showPredictionForm(Model model) {
//        model.addAttribute("studentInput", new StudentInput());
//        model.addAttribute("branches", branchRepository.findAll());
//        model.addAttribute("quotaTags", List.of("GEN", "PWD", "DEF"));
//        model.addAttribute("regions", List.of("Konkan", "Marathwada", "Northern", "Vidarbha", "Western"));
//        model.addAttribute("categories", categoryService.getAllCategories());
//
//
//
//        // Add list of locations
//        List<String> locations = Arrays.asList(
//                "Yavatmal", "Wagholi", "Wada", "Solapur", "Sangli", "Ratnagiri", "Pune", "Pimpri",
//                "Panvel", "Otur", "Osmanabad", "Navi Mumbai", "Nashik", "Narhe", "Nanded", "Nagpur",
//                "Mumbai", "Malegaon", "Lonikand", "Loni Kalbhor", "Lonavala", "Kopargaon", "Kolhapur",
//                "Karad", "Jalgaon", "Chikhli", "Chandwad", "Chandrapur", "Beed", "Baramati",
//                "Aurangabad", "Amravati", "Ambi", "Alandi"
//        );
//        model.addAttribute("locations", locations);
//
//        model.addAttribute("categories", categoryRepository.findAll()); // ✅ Add this
//        return "predict-college";
//    }

    @GetMapping("/predict")
    public String showPredictForm(Model model) {
        model.addAttribute("studentInput", new StudentInput());

        model.addAttribute("departments", branchRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("regions", instituteRepository.findDistinctRegions());
        model.addAttribute("capRounds", cutoffRepository.findDistinctCapRounds());
        model.addAttribute("quotaTags", cutoffRepository.findDistinctQuotaTags());

        // Static dropdowns
        model.addAttribute("homeUniversityStatuses", List.of("HU", "OHU"));
        model.addAttribute("minorityStatuses", List.of("YES", "NO"));

        return "predict-college";
    }

    @PostMapping("/predict")
    public String handlePredictCollege(@ModelAttribute("studentInput") StudentInput input, Model model) {
        List<CollegeResultDto> results = predictionService.predictColleges(input);
        model.addAttribute("results", results);
        return "predict-result";
    }


    // ----------------------
    // HANDLE COLLEGE PREDICTION
    // ----------------------
//    @PostMapping("/predict")
//    public String handlePredictCollege(@ModelAttribute("studentInput") StudentInput input, Model model) {
//        List<CollegeResultDto> results = predictionService.predictColleges(input);
//        model.addAttribute("results", results);
//        return "predict-result";
//    }
////    @PostMapping("/predict")
//    public String predictColleges(@ModelAttribute StudentInput input, Model model) {
//        List<CollegeResultDto> results = predictionService.predictColleges(input);
//        model.addAttribute("results", results);
//        return "prediction-result";
//    }

//@GetMapping("/scholarships")
//public String getScholarships(@RequestParam(name = "category", required = false) String category, Model model) {
//    List<Scholarship> scholarships = (category == null || category.isEmpty())
//            ? scholarshipService.getAllScholarships()
//            : scholarshipService.getScholarshipByCategory(category);
//
//    model.addAttribute("scholarships", scholarships);
//    model.addAttribute("category", category);
//    return "scholarship-list";
//}

@GetMapping("/scholarships")
public String showScholarships(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "keyword", required = false) String keyword,
        Model model) {

    int pageSize = 4;
    Page<Scholarship> scholarshipPage;

    if (keyword != null && !keyword.trim().isEmpty()) {
        scholarshipPage = scholarshipService.searchScholarships(keyword, PageRequest.of(page, pageSize));
    } else {
        scholarshipPage = scholarshipService.getAllScholarships(PageRequest.of(page, pageSize));
    }

    model.addAttribute("scholarships", scholarshipPage.getContent());
    model.addAttribute("totalPages", scholarshipPage.getTotalPages());
    model.addAttribute("currentPage", page);
    model.addAttribute("keyword", keyword);
    return "scholarship-list";
}

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

        return "timeline-lists";
    }

    @GetMapping("/notifications")
    public String showNotificationForm(Model model) {
        model.addAttribute("notification", new Notification());
        model.addAttribute("notifications", notificationService.getAllNotifications()); // For listing
        return "stdnotification"; // Thymeleaf HTML page name
    }

    @GetMapping("/grievance")
    public String viewStudentGrievance(Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("grievances", grievanceService.getGrievancesByUser(user));
        return "student-grievance-list";
    }

    @GetMapping("/grievance/new")
    public String newGrievanceForm(Model model){
        model.addAttribute("grievance", new Grievance());
        return "grievance-form";
    }

    @PostMapping("/save")
    public String saveGrievance(@ModelAttribute Grievance grievance, Principal principal){
        User user = userService.findByUsername(principal.getName());
        grievance.setUser(user);
        grievanceService.saveGrievance(grievance);
        return "redirect:/student/grievance";
    }

}
