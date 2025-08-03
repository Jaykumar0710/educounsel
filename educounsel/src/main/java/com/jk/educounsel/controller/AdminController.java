package com.jk.educounsel.controller;

import com.jk.educounsel.model.*;
import com.jk.educounsel.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private InstituteService instituteService;
    @Autowired
    private CutoffService cutoffService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ScholarshipService scholarshipService;
    @Autowired
    private TimelineService timelineService;

    @Autowired
    private NotificationService notificationService;
    // missing service , repo, documentChecklist
    private final String uploadNotificationDir = "notification/";


    @GetMapping("/dashboard")
    public String showDashboard(Model model, Principal principal) {
        String username = principal.getName();
        String email = principal.getName();
        User user = userService.findByEmail(email);
        model.addAttribute("adminName", username);
        model.addAttribute("adminEmail", email);

        model.addAttribute("userCount", userService.countUsers());
        model.addAttribute("instituteCount", instituteService.countInstitutes());
        model.addAttribute("branchCount", branchService.countBranches());
        model.addAttribute("categoryCount", categoryService.countCategories());
        model.addAttribute("cutoffCount", cutoffService.countCutoffs());
        model.addAttribute("scholarshipCount", scholarshipService.countScholarships());
        model.addAttribute("timelineCount", timelineService.countTimelines());
        model.addAttribute("notificationCount", notificationService.countTimelines());


        List<Timeline> latestTimelines = timelineService.findTop5ByOrderByDateDesc();
        model.addAttribute("latestTimelines", latestTimelines);
        return "admin-dashboard";
    }


    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>USER Manangement<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //Get All Users
    @GetMapping("/users")
    public String listUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String role,
            Model model) {

        Page<User> userPage = userService.searchAndFilter(keyword, role, page, size);

        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("totalUsers", userPage.getTotalElements());
        model.addAttribute("keyword", keyword);
        model.addAttribute("role", role);

        return "admin-users";
    }


    //ADD User Form
    @GetMapping("/users/new")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "admin-add-user";
    }

    @PostMapping("/users")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    //Edit User Form
    @GetMapping("/users/edit/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());
            return "admin-edit-user";
        } else {
            model.addAttribute("message", "User not found");
            return "error";
        }

    }

    @PostMapping("/users/update")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user); // make sure this method updates by id
        return "redirect:/admin/users";
    }


    //Delete User
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes) {
        // Get current user
        User currentUser = userService.findByUsername(principal.getName());

        try {
            userService.deleteUser(id, currentUser.getId());
            redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/admin/users";
    }
// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Institute Management<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //Get List of Institute
//    @GetMapping("/institutes")
//    public String listInstitutes(Model model){
//        model.addAttribute("institute", instituteService.getAllInstitutes());
//        return "institutes-list";
//
//    }

    //Pagination for Institute
    @GetMapping("/institutes")
    public String listInstitutes(@RequestParam(value = "keyword", required = false) String keyword,
                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                 Model model) {

        Pageable pageable = PageRequest.of(page, 10);
        Page<Institute> institutePage;

        if (keyword != null && !keyword.trim().isEmpty()) {
            institutePage = instituteService.findByNameContainingIgnoreCase(keyword, pageable);
        } else {
            institutePage = instituteService.findAll(pageable);
        }

        model.addAttribute("institutes", institutePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", institutePage.getTotalPages());
        model.addAttribute("keyword", keyword);

        return "institute-list"; // Must match HTML file name
    }




    //Institute form for adding new institute
    @GetMapping("/institutes/new")
    public String newInstituteForms(Model model){
        model.addAttribute("institute", new Institute());
        return "institute-form";
    }

    //Saving new Institute
//    @PostMapping("/institutes")
//    public String saveInstitute(@ModelAttribute Institute institute){
//        instituteService.saveInstitute(institute);
//        return "redirect:/admin/institute";
//    }

    //Edit Institute
    @GetMapping("/institute/edit/{code}")
    public String editInstitute(@PathVariable("code") String code, Model model) {
        Optional<Institute> optionalInstitute = instituteService.getById(code);
        if (optionalInstitute.isPresent()) {
            model.addAttribute("institute", optionalInstitute.get());
        } else {
            return "redirect:/admin/institutes"; // or a 404 page
        }
        return "institute-form";
    }


    @GetMapping("/institute/new")
    public String newInstituteForm(Model model) {
        model.addAttribute("institute", new Institute());
        return "institute-form";
    }

    @PostMapping("/institute/save")
    public String saveInstitute(@ModelAttribute Institute institute) {
        instituteService.save(institute);
        return "redirect:/admin/institutes";
    }


    //Delete Institute
    @GetMapping("/institute/delete/{code}")
    public String deleteInstitute(@PathVariable("code") String code) {
        instituteService.deleteById(code);  // Ensure this method exists
        return "redirect:/admin/institutes"; // or your actual list page
    }



    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Branch Management<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @GetMapping("/branches")
    public String listBranches(@RequestParam(value = "keyword", required = false)String keyword,
                               @RequestParam(value = "page",defaultValue = "0") int page, Model model){

        Pageable pageable = PageRequest.of(page, 10);
        Page<Branch> branchePage;
        model.addAttribute("instituteList", instituteService.getAllInstitutes());


        if (keyword != null && !keyword.trim().isEmpty()){
            branchePage=branchService.findByBranchNameContainingIgnoreCase(keyword,pageable);
        }else {
            branchePage=branchService.findAll(pageable);
        }

        model.addAttribute("branches", branchePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages",branchePage.getTotalPages());
        model.addAttribute("keyword",keyword);
        model.addAttribute("instituteList", instituteService.getAllInstitutes());


        return "branch-list";
    }

    //Branch Frorm for adding new BranchFrom
    @GetMapping("/branches/new")
    public String newBranchesForms(Model model){
        model.addAttribute("branch", new Branch());
        model.addAttribute("instituteList", instituteService.getAllInstitutes());

        return "branch-form";
    }

    //Saving New Branch
    @PostMapping("/branches/save")
    public String saveBranch(@ModelAttribute Branch branch){
        branchService.save(branch);
        return "redirect:/admin/branches";
    }

    //edit Branches
    @GetMapping("/branches/edit/{id}")
    public String editBranch(@PathVariable("id") String code, Model model){
        Optional<Branch>optionalBranch = branchService.getById(code);
        if (optionalBranch.isPresent()){
            model.addAttribute("branch", optionalBranch.get());
            model.addAttribute("instituteList", instituteService.getAllInstitutes());

        }else {
            return "redirect:/admin/branches";
        }
        return "branch-form";
    }

    //Delete Branch

    @GetMapping("/branches/delete/{id}")
    public String deleteBranch(@PathVariable("id") String code){
        branchService.deleteById(code);
        return "redirect:/admin/branches";
    }


    //<<<<<<<<<<<<<<<<<<<<<<<<<<<Category Controller>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @GetMapping("/categories")
    public String listCategory(@RequestParam(value = "keyword", required = false)String keyword,
                               @RequestParam(value = "page", defaultValue = "0") int page, Model model){
        Pageable pageable =PageRequest.of(page, 10);

        Page <Category> categoryPage;

        if (keyword!=null && !keyword.trim().isEmpty()){
            categoryPage= categoryService.findByCategoryCodeContainingIgnoreCase(keyword,pageable);
        }else {
            categoryPage=categoryService.findAll(pageable);
        }
        model.addAttribute("categories", categoryPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", categoryPage.getTotalPages());
        model.addAttribute("keyword", keyword);

        return "category-list";
    }

    @GetMapping("/categories/new")
    public String newCategoriesForm(Model model){
        model.addAttribute("category",new Category());
        return "category-form";
    }

    //Saving New Category
    @PostMapping("/categories/save")
    public String  saveCategory(@ModelAttribute Category category){
        categoryService.saveCategory(category);
        return "redirect:/admin/categories";
    }

    // Edit Category
    @GetMapping("/categories/edit/{id}")
    public String editCategory(@PathVariable("id") String code, Model model) {
        Optional<Category> optionalCategory = categoryService.getCategoryById(code);
        if (optionalCategory.isPresent()) {
            model.addAttribute("category", optionalCategory.get());
            return "category-form"; // Show form with data
        } else {
            return "redirect:/admin/categories"; // Corrected double-slash in URL
        }
    }

    // Delete Category
    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable("id") String code) {
        categoryService.deleteCategory(code); // Corrected line
        return "redirect:/admin/categories";
    }
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Timeline>>>>>>>>>>>>>>>>>>>>>>>>>



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
            model.addAttribute("timeline", optionalTimeline.get());
        }else {
            return"redirect:/admin/timelines";
        }
        return "timeline-form";
    }



    //>>>>>>>>>>>>>>>>>>>>>>>>>>>Scholarship<<<<<<<<<<<<<<<<<<<


    @GetMapping("/scholarships")
    public String showScholarships(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {

        int pageSize = 5;
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
        return "/admin/Admin-Scholarship-list";
    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Notification Controllers<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    // Show the Notification form page
    @GetMapping("/notifications")
    public String showNotificationForm(Model model) {
        model.addAttribute("notification", new Notification());
        model.addAttribute("notifications", notificationService.getAllNotifications()); // For listing
        return "admNotification"; // Thymeleaf HTML page name
    }

    // Save the notification form + uploaded file
    @PostMapping("/saveNotification")
    public String saveNotification(@ModelAttribute("notification") Notification notification,
                                   @RequestParam("file") MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            // Get original filename
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename(); // Avoid overwriting files

            // Ensure directory exists
            File directory = new File(uploadNotificationDir);
            if (!directory.exists()) {
                directory.mkdirs(); // create folders if not present
            }

            // Save file
            Path filePath = Paths.get(uploadNotificationDir + filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Save file path to notification object
            notification.setFilePath(filePath.toString());
        }

        // Save notification to DB
        notificationService.saveNotification(notification);

        // Redirect to list
        return "redirect:/admin/notifications";
    }

    // Delete notification by ID
    @GetMapping("/deleteNotification/{id}")
    public String deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return "redirect:/admin/notifications";
    }

    // Edit existing notification
    @GetMapping("/editNotification/{id}")
    public String editNotification(@PathVariable Long id, Model model) {
        Notification notification = notificationService.getNotification(id);
        if (notification != null) {
            model.addAttribute("notification", notification);
            model.addAttribute("notifications", notificationService.getAllNotifications());
        } else {
            System.out.println("Notification with ID " + id + " not found.");
        }
        return "admNotification";
    }

}
