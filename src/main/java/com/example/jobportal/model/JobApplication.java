package com.example.jobportal.model;

import com.example.jobportal.Role.EApplicationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="job_applications", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id","job_id"})
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobApplication {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="job_id", nullable = false)
    private Job job;

    @Enumerated(EnumType.STRING)
    private EApplicationStatus appStatus;

    private LocalDateTime appliedAt;

    @PrePersist
    protected void onApply(){
        this.appliedAt = LocalDateTime.now();
        this.appStatus= EApplicationStatus.PENDING;
    }

    @Column(name="resume_path")
    private String resumePath;

}
