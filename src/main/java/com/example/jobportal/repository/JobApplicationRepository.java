package com.example.jobportal.repository;

import com.example.jobportal.model.Job;
import com.example.jobportal.model.JobApplication;
import com.example.jobportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    List<JobApplication> findByUser(User user);
    Optional<JobApplication> findByUserAndJob(User user, Job job);

}
