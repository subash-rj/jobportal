package com.example.jobportal.controller;

import com.example.jobportal.dto.JobRequestDTO;
import com.example.jobportal.dto.JobResponseDTO;
import com.example.jobportal.model.Job;
import com.example.jobportal.service.JobServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobServiceImpl jobServiceImpl;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // Only ADMINs can post jobs
    public ResponseEntity<JobResponseDTO> createJob(@RequestBody JobRequestDTO request) {
        return ResponseEntity.ok(jobServiceImpl.createJobPost(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CANDIDATE')")
    public ResponseEntity<List<JobResponseDTO>> getAllCurrentJobs(){
        return ResponseEntity.ok(jobServiceImpl.getAllJobs());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CANDIDATE')")
    public ResponseEntity<JobResponseDTO> getJobsById(@PathVariable Long id){
        return ResponseEntity.ok(jobServiceImpl.getJobsById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<JobResponseDTO> updateJobDetails(@PathVariable Long id,
                                                           @RequestBody JobRequestDTO dto){
        return ResponseEntity.ok(jobServiceImpl.updateJobsById(id,dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteJobPost(@PathVariable Long id){
        jobServiceImpl.deleteJobPost(id);
        return ResponseEntity.noContent().build();
    }
}
