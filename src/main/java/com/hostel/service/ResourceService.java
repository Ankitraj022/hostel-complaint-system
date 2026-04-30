package com.hostel.service;

import com.hostel.entity.Resource;
import com.hostel.entity.ResourceRequest;
import com.hostel.entity.User;
import com.hostel.exception.ResourceNotFoundException;
import com.hostel.repository.ResourceRepository;
import com.hostel.repository.ResourceRequestRepository;
import com.hostel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ResourceRequestRepository resourceRequestRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    public List<ResourceRequest> getRequestsByStudent(Long studentId) {
        return resourceRequestRepository.findByStudentId(studentId);
    }

    public List<ResourceRequest> getAllRequests() {
        return resourceRequestRepository.findAll();
    }

    public ResourceRequest createResourceRequest(Long studentId, Long resourceId, int quantity) {
        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        ResourceRequest req = new ResourceRequest();
        req.setResource(resource);
        req.setStudent(student);
        req.setRequestedQuantity(quantity);
        req.setStatus("Pending");
        return resourceRequestRepository.save(req);
    }

    public ResourceRequest createCustomResourceRequest(Long studentId, String customResourceName, int quantity) {
        Resource freshResource = new Resource();
        freshResource.setName(customResourceName);
        freshResource.setQuantity(0);
        freshResource.setStatus("New Request");
        freshResource = resourceRepository.save(freshResource);

        return createResourceRequest(studentId, freshResource.getId(), quantity);
    }

    public ResourceRequest processRequest(Long requestId, String status) {
        ResourceRequest req = resourceRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));

        if ("Approved".equals(status)) {
            Resource res = req.getResource();
            if (res.getQuantity() >= req.getRequestedQuantity()) {
                res.setQuantity(res.getQuantity() - req.getRequestedQuantity());
                if (res.getQuantity() == 0)
                    res.setStatus("Out of Stock");
                resourceRepository.save(res);
            } else {
                req.setStatus("Rejected (Insufficient Stock)");
                return resourceRequestRepository.save(req);
            }
        }

        req.setStatus(status);
        return resourceRequestRepository.save(req);
    }
}
