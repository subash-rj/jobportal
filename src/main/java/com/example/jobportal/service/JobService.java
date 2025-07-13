package com.example.jobportal.service;

import com.example.jobportal.dto.JobRequestDTO;
import com.example.jobportal.dto.JobResponseDTO;

import java.util.List;

public interface JobService {

    JobResponseDTO createJobPost(JobRequestDTO dto);
    List<JobResponseDTO> getAllJobs();
    JobResponseDTO getJobsById(Long id);
    JobResponseDTO updateJobsById(Long id, JobRequestDTO dto);
    void deleteJobPost(Long id);

}
