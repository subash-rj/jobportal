package com.example.jobportal.service;

import com.example.jobportal.Role.EApplicationStatus;
import com.example.jobportal.dto.JobApplicationRequestDTO;
import com.example.jobportal.dto.JobApplicationResponseDTO;
import com.example.jobportal.exception.DuplicateApplicationException;
import com.example.jobportal.exception.ResourceNotFoundException;
import com.example.jobportal.model.Job;
import com.example.jobportal.model.JobApplication;
import com.example.jobportal.model.User;
import com.example.jobportal.repository.JobApplicationRepository;
import com.example.jobportal.repository.JobRepository;
import com.example.jobportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService{

    private final JobApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;


    @Override
    public JobApplicationResponseDTO applyToJob(JobApplicationRequestDTO dto, String username) {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("Username not found "+username));

        Job job = jobRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Job not found "+dto.getId()));

        applicationRepository.findByUserAndJob(user,job).ifPresent(app ->{
            throw new DuplicateApplicationException("Application already exists");
        });

        JobApplication jobApp = JobApplication.builder()
                        .job(job)
                        .user(user)
                        .build();

        return mapToDTO(applicationRepository.save(jobApp));

    }

    private JobApplicationResponseDTO mapToDTO(JobApplication save) {

        return JobApplicationResponseDTO.builder()
                .id(save.getId())
                .companyName(save.getJob().getCompany().getName())
                .jobTitle(save.getJob().getTitle())
                .appStatus(save.getAppStatus())
                .resumePath(save.getResumePath())
                .appliedBy(save.getUser().getName())
                .build();

    }

    @Override
    public List<JobApplicationResponseDTO> getApplicationsByUser(String username) {

        User candidate = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return applicationRepository.findByUser(candidate).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

    }

    @Override
    public List<JobApplicationResponseDTO> getAllApplications() {
        return applicationRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<JobApplicationResponseDTO> getApplicationsByJob(Long jobId) {
        Job jobPost = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        return applicationRepository.findAll().stream()
                .filter(app -> app.getJob().getId().equals(jobId))
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public JobApplicationResponseDTO updateApplicationStatus(Long applicationId, String statusStr) {
        JobApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        EApplicationStatus status;
        try {
            status = EApplicationStatus.valueOf(statusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: use PENDING, ACCEPTED or REJECTED");
        }

        application.setAppStatus(status);
        applicationRepository.save(application);
        return mapToDTO(application);
    }

    @Override
    public JobApplicationResponseDTO applyToJobWithResume(Long jobId, MultipartFile resume, String username) {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("Username not found "+username));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found "+jobId));

        applicationRepository.findByUserAndJob(user,job).ifPresent(app ->{
            throw new DuplicateApplicationException("Application already exists");
        });

        String resumeFilePath = storeResumeFile(resume,user.getId(),job.getId());

        JobApplication jobApp = JobApplication.builder()
                .job(job)
                .user(user)
                .resumePath(resumeFilePath)
                .build();

        return mapToDTO(applicationRepository.save(jobApp));

    }

    private String storeResumeFile(MultipartFile resume, Long userId, Long jobId) {

        try{
            String dirPath="resumes/";
            Files.createDirectories(Paths.get(dirPath));

            String fileName = "resume_user"+userId+"_job"+jobId+"_"+resume.getOriginalFilename();
            Path filePath = Paths.get(dirPath+fileName);
            Files.write(filePath, resume.getBytes());

            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Resume Upload failed "+e);
        }

    }

}
