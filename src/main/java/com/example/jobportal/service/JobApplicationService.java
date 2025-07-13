package com.example.jobportal.service;

import com.example.jobportal.dto.JobApplicationRequestDTO;
import com.example.jobportal.dto.JobApplicationResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface JobApplicationService {

    JobApplicationResponseDTO applyToJob(JobApplicationRequestDTO dto, String username);
    List<JobApplicationResponseDTO> getApplicationsByUser(String username);
    List<JobApplicationResponseDTO> getAllApplications();
    List<JobApplicationResponseDTO> getApplicationsByJob(Long jobId);
    JobApplicationResponseDTO updateApplicationStatus(Long applicationId, String status);
    JobApplicationResponseDTO applyToJobWithResume(Long id, MultipartFile resume, String username);

}
