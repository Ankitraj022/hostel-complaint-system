package com.hostel.controller;

import com.hostel.entity.Complaint;
import com.hostel.entity.User;
import com.hostel.repository.UserRepository;
import com.hostel.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard")
    public String staffDashboard(Model model, Authentication authentication) {
        User user = userRepository.findFirstByEmail(authentication.getName()).orElse(null);
        if (user != null) {
            model.addAttribute("complaints", complaintService.getComplaintsByStaff(user.getId()));
            model.addAttribute("user", user);
        }
        return "staff_dashboard";
    }

    @PostMapping("/complaints/{id}/status")
    public String updateStatus(@PathVariable("id") Long id, @RequestParam("status") String status) {
        complaintService.updateComplaintStatus(id, status);
        return "redirect:/staff/dashboard?success";
    }
}
