package com.example.jobportal.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobRequestDTO {
    private String title;
    private String description;
    private String location;
    private Long companyId;
    private Double salary;
    private String jobType;
}
