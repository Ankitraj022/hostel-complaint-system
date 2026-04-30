package com.hostel.controller;

import com.hostel.entity.Complaint;
import com.hostel.entity.User;
import com.hostel.repository.UserRepository;
import com.hostel.service.ComplaintService;
import com.hostel.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard")
    public String studentDashboard(Model model, Authentication authentication) {
        User user = userRepository.findFirstByEmail(authentication.getName()).orElse(null);
        if (user != null) {
            model.addAttribute("complaints", complaintService.getComplaintsByStudent(user.getId()));
            model.addAttribute("resources", resourceService.getAllResources());
            model.addAttribute("myRequests", resourceService.getRequestsByStudent(user.getId()));
            model.addAttribute("user", user);
        }
        model.addAttribute("newComplaint", new Complaint());
        return "student_dashboard";
    }

    @PostMapping("/complaints")
    public String raiseComplaint(@ModelAttribute("newComplaint") Complaint complaint, Authentication authentication) {
        User user = userRepository.findFirstByEmail(authentication.getName()).orElse(null);
        if (user != null) {
            complaintService.createComplaint(user.getId(), complaint);
        }
        return "redirect:/student/dashboard?success";
    }

    @PostMapping("/resources/request")
    public String requestResource(@RequestParam("resourceId") Long resourceId,
            @RequestParam(value = "customResource", required = false) String customResource,
            @RequestParam("quantity") int quantity,
            Authentication authentication) {
        User user = userRepository.findFirstByEmail(authentication.getName()).orElse(null);
        if (user != null) {
            if (resourceId == -1 && customResource != null && !customResource.trim().isEmpty()) {
                resourceService.createCustomResourceRequest(user.getId(), customResource, quantity);
            } else {
                resourceService.createResourceRequest(user.getId(), resourceId, quantity);
            }
        }
        return "redirect:/student/dashboard?reqSuccess";
    }
}
