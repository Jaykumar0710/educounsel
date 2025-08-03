package com.jk.educounsel.controller;

import com.jk.educounsel.model.Grievance;
import com.jk.educounsel.model.User;
import com.jk.educounsel.service.GrievanceService;
import com.jk.educounsel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/officer")
public class OfficerController {

    @Autowired
    private UserService userService;

    @Autowired
    private GrievanceService grievanceService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username);

        long pendingCount = grievanceService.countByStatus("Pending");
        long resolvedCount = grievanceService.countByStatus("Resolved");

        model.addAttribute("user", user);
        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("resolvedCount", resolvedCount);
        model.addAttribute("recentGrievances", grievanceService.getRecentGrievances()); // optional

        return "officer-dashboard";
    }


    @GetMapping("/grievances")
    public String showAllGrievances(Model model) {
        List<Grievance> grievances = grievanceService.findAll();
        long pendingCount = grievanceService.countByStatus("Pending");
        long resolvedCount = grievanceService.countByStatus("Resolved");

        model.addAttribute("grievances", grievances);
        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("resolvedCount", resolvedCount);
        return "officer-grievance-list";
    }


    @GetMapping("/grievance/resolve")
    public String resolveGrievanceForm(@RequestParam Long id, Model model) {
        model.addAttribute("grievance", grievanceService.getGrievanceById(id));
        return "grievance-resolve-form";
    }
    @GetMapping("/grievance/resolve/{id}")
    public String showResolutionForm(@PathVariable Long id, Model model) {
        Grievance grievance = grievanceService.getGrievanceById(id);
        model.addAttribute("grievance", grievance);
        return "grievance-resolve-form";// Make this Thymeleaf HTML page
    }

    @PostMapping("/grievance/resolve")
    public String submitResolution(@ModelAttribute Grievance grievance) {
        // Retain original grievance to avoid losing data like user
        Grievance existing = grievanceService.getGrievanceById(grievance.getId());
        existing.setResolution(grievance.getResolution());
        existing.setStatus("Resolved");
        grievanceService.saveGrievance(existing);
        return "redirect:/officer/grievances";
    }

}

