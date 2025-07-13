package com.example.jobportal.dto;

import com.example.jobportal.Role.EApplicationStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApplicationResponseDTO {

    private Long id;
    private String jobTitle;
    private String companyName;
    private EApplicationStatus appStatus;
    private String appliedBy;

    private String resumePath;

}
