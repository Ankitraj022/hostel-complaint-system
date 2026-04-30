package com.hostel;

import com.hostel.entity.Complaint;
import com.hostel.entity.Resource;
import com.hostel.entity.Role;
import com.hostel.entity.User;
import com.hostel.repository.ComplaintRepository;
import com.hostel.repository.ResourceRepository;
import com.hostel.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ComplaintRepository complaintRepository;
    private final ResourceRepository resourceRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(UserRepository userRepository, ComplaintRepository complaintRepository,
            ResourceRepository resourceRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.complaintRepository = complaintRepository;
        this.resourceRepository = resourceRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() < 20) {
            // Seed Admin
            if (userRepository.findFirstByEmail("admin@hostel.com").isEmpty()) {
                User admin = new User();
                admin.setName("Admin User");
                admin.setEmail("admin@hostel.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);
            }

            // Seed Staff
            User staff1 = createUser("Maintenance John", "staff@hostel.com", Role.STAFF);
            User staff2 = createUser("Electrician Mike", "mike@hostel.com", Role.STAFF);
            User staff3 = createUser("Plumber Dave", "dave@hostel.com", Role.STAFF);
            User staff4 = createUser("Carpenter Steve", "steve@hostel.com", Role.STAFF);
            User staff5 = createUser("Cleaner Sarah", "sarah@hostel.com", Role.STAFF);
            User staff6 = createUser("IT Support Leo", "leo@hostel.com", Role.STAFF);
            User staff7 = createUser("Security Paul", "paul@hostel.com", Role.STAFF);

            // Seed Students
            User student1 = createUser("Student Alice", "student@hostel.com", Role.STUDENT);
            User student2 = createUser("Student Bob", "bob@hostel.com", Role.STUDENT);
            User student3 = createUser("Student Charlie", "charlie@hostel.com", Role.STUDENT);
            User student4 = createUser("Student Diana", "diana@hostel.com", Role.STUDENT);
            User student5 = createUser("Student Ethan", "ethan@hostel.com", Role.STUDENT);
            User student6 = createUser("Student Fiona", "fiona@hostel.com", Role.STUDENT);
            User student7 = createUser("Student George", "george@hostel.com", Role.STUDENT);
            User student8 = createUser("Student Hannah", "hannah@hostel.com", Role.STUDENT);
            User AnkitRaj = createUser("Student Ankit", "ankit@hostel.com", Role.STUDENT);
            // Seed Resources
            if (resourceRepository.count() == 0) {
                resourceRepository.saveAll(Arrays.asList(
                        createResource("Desk Chair", 50, "Available"),
                        createResource("Study Table", 25, "Available"),
                        createResource("Ceiling Fan", 10, "Low Stock"),
                        createResource("Wi-Fi Router", 5, "Low Stock"),
                        createResource("Water Purifier", 0, "Out of Stock"),
                        createResource("Mattress", 15, "Available"),
                        createResource("Room Keys", 30, "Available")));
            } // Seed Complaints
            complaintRepository.saveAll(Arrays.asList(
                    createComplaint("Leaking Tap", "Plumbing", "101-A", "Tap has been dripping for two days.",
                            "Pending", student1, null),
                    createComplaint("No Wi-Fi", "WiFi", "205-B", "Internet keeps disconnecting.", "Assigned", student2,
                            staff1),
                    createComplaint("Broken Fan", "Electricity", "101-A", "Ceiling fan making loud noise.",
                            "In Progress", student1, staff2),
                    createComplaint("Dirty Corridor", "Cleanliness", "Floor 2", "The corridor hasn't been swept.",
                            "Pending", student3, null),
                    createComplaint("Bad Food Quality", "Mess", "N/A", "Dinner was undercooked yesterday.", "Resolved",
                            student4, staff3),
                    createComplaint("AC Not Cooling", "Electricity", "305-C", "Air conditioner blowing warm air.",
                            "Pending", student5, null),
                    createComplaint("Clogged Drain", "Plumbing", "110-B", "Bathroom drain is completely blocked.",
                            "Assigned", student2, staff3),
                    createComplaint("Pest Issue", "Cleanliness", "401-A", "Saw rodents near the garbage bin.",
                            "In Progress", student5, staff1),
                    createComplaint("Faulty Router", "WiFi", "202-A", "Router physically damaged.", "Resolved",
                            student3, staff1),
                    createComplaint("Broken Desk", "Carpentry", "108-C", "Study desk leg is broken.", "Pending",
                            student1, null),
                    createComplaint("Flickering Lights", "Electricity", "505-D", "Tube light flickers constantly.",
                            "Pending", student4, null),
                    createComplaint("Water Shortage", "Plumbing", "Floor 3", "No water in the morning.", "Assigned",
                            student2, staff3),
                    createComplaint("Slow Internet", "WiFi", "208-B", "Speed is less than 1mbps.", "Resolved", student5,
                            staff2),
                    createComplaint("Mosquito Net Torn", "Cleanliness", "101-A", "Window mesh has a large hole.",
                            "In Progress", student1, staff1),
                    createComplaint("Mess Delay", "Mess", "N/A", "Breakfast served 1 hour late today.", "Pending",
                            student3, null)));

            System.out.println("Database expanded with large dataset (15 complaints, 5 resources, 9 users)!");
        }
    }

    private User createUser(String name, String email, Role role) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(email.split("@")[0] + "123")); // simple password strategy
        user.setRole(role);
        return userRepository.save(user);
    }

    private Resource createResource(String name, int quantity, String status) {
        Resource r = new Resource();
        r.setName(name);
        r.setQuantity(quantity);
        r.setStatus(status);
        return r;
    }

    private Complaint createComplaint(String title, String category, String room, String desc, String status,
            User student, User staff) {
        Complaint c = new Complaint();
        c.setTitle(title);
        c.setCategory(category);
        c.setRoomNumber(room);
        c.setDescription(desc);
        c.setStatus(status);
        c.setStudent(student);
        c.setStaff(staff);
        return c;
    }
}
