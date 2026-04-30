package com.hostel.service;

import com.hostel.entity.Complaint;
import com.hostel.entity.User;
import com.hostel.repository.ComplaintRepository;
import com.hostel.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ComplaintServiceTest {

    @Mock
    private ComplaintRepository complaintRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ComplaintService complaintService;

    @Test
    public void testAssignComplaintToStaff_Success() {
        Long complaintId = 1L;
        Long staffId = 2L;

        Complaint complaint = new Complaint();
        complaint.setId(complaintId);
        complaint.setStatus("Pending");

        User staff = new User();
        staff.setId(staffId);

        when(complaintRepository.findById(complaintId)).thenReturn(Optional.of(complaint));
        when(userRepository.findById(staffId)).thenReturn(Optional.of(staff));
        when(complaintRepository.save(any(Complaint.class))).thenReturn(complaint);

        Complaint updatedComplaint = complaintService.assignComplaintToStaff(complaintId, staffId);

        assertEquals("Assigned", updatedComplaint.getStatus());
        assertEquals(staffId, updatedComplaint.getStaff().getId());
        verify(complaintRepository, times(1)).save(complaint);
    }
}
