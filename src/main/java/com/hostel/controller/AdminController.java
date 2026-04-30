package com.hostel.controller;

import com.hostel.entity.Role;
import com.hostel.repository.UserRepository;
import com.hostel.service.ComplaintService;
import com.hostel.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("complaints", complaintService.getAllComplaints());
        model.addAttribute("resources", resourceService.getAllResources());
        model.addAttribute("resourceRequests", resourceService.getAllRequests());
        model.addAttribute("staffMembers", userRepository.findAll().stream()
                .filter(u -> u.getRole() == Role.STAFF)
                .collect(Collectors.toList()));
        return "admin_dashboard";
    }

    @PostMapping("/complaints/{id}/assign")
    public String assignComplaint(@PathVariable("id") Long id, @RequestParam("staffId") Long staffId) {
        complaintService.assignComplaintToStaff(id, staffId);
        return "redirect:/admin/dashboard?success";
    }

    @PostMapping("/resources/request/{id}/process")
    public String processResourceRequest(@PathVariable("id") Long id, @RequestParam("status") String status) {
        resourceService.processRequest(id, status);
        return "redirect:/admin/dashboard?resSuccess";
    }
}
