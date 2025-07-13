package com.example.jobportal.service;

import com.example.jobportal.dto.JobRequestDTO;
import com.example.jobportal.dto.JobResponseDTO;
import com.example.jobportal.exception.ResourceNotFoundException;
import com.example.jobportal.model.CompanyEntity;
import com.example.jobportal.model.Job;
import com.example.jobportal.repository.CompanyRepository;
import com.example.jobportal.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService{

    private final JobRepository jobRepository;

    private final CompanyRepository companyRepository;

    @Override
    public JobResponseDTO createJobPost(JobRequestDTO dto) {

        CompanyEntity company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found "+dto.getCompanyId()));

        Job job = Job.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .location(dto.getLocation())
                .company(company)
                .salary(dto.getSalary())
                .jobType(dto.getJobType())
                .createdAt(LocalDateTime.now())
                .build();

        return mapToDTO(jobRepository.save(job));
    }



    @Override
    public List<JobResponseDTO> getAllJobs() {

        return jobRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

    }

    @Override
    public JobResponseDTO getJobsById(Long id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found "+id));

        return mapToDTO(job);

    }

    @Override
    public JobResponseDTO updateJobsById(Long id, JobRequestDTO dto) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found "+id));

        CompanyEntity company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found "+id));

        job.setJobType(dto.getJobType());
        job.setCompany(company);
        job.setDescription(dto.getDescription());
        job.setSalary(dto.getSalary());
        job.setLocation(dto.getLocation());
        job.setTitle(dto.getTitle());

        return mapToDTO(jobRepository.save(job));

    }

    @Override
    public void deleteJobPost(Long id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found "+ id));

        jobRepository.delete(job);

    }

    private JobResponseDTO mapToDTO(Job job) {

        return JobResponseDTO.builder()
                .id(job.getId())
                .salary(job.getSalary())
                .jobType(job.getJobType())
                .title(job.getTitle())
                .companyName(job.getCompany().getName())
                .description(job.getDescription())
                .location(job.getLocation())
                .build();
    }
}
