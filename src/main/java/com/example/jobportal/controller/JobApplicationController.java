package com.example.jobportal.controller;

import com.example.jobportal.service.JobApplicationServiceImpl;
import com.example.jobportal.dto.JobApplicationRequestDTO;
import com.example.jobportal.dto.JobApplicationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class JobApplicationController {

    private final JobApplicationServiceImpl applicationService;

    @PostMapping("/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<JobApplicationResponseDTO> apply(@RequestParam("jobId") Long id,
                                                           @RequestParam("resume") MultipartFile resume,
                                                           Principal principal) {
        return ResponseEntity.ok(applicationService.applyToJobWithResume(id,resume,principal.getName()));
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<List<JobApplicationResponseDTO>> getMyApplications(Principal principal) {
        return ResponseEntity.ok(applicationService.getApplicationsByUser(principal.getName()));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<JobApplicationResponseDTO>> getAll() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @GetMapping("/job/{jobId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<JobApplicationResponseDTO>> getByJob(@PathVariable Long jobId) {
        return ResponseEntity.ok(applicationService.getApplicationsByJob(jobId));
    }

    @PutMapping("/{applicationId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<JobApplicationResponseDTO> updateStatus(@PathVariable Long applicationId,
                                                                  @RequestParam String status) {
        return ResponseEntity.ok(applicationService.updateApplicationStatus(applicationId, status));
    }

}
