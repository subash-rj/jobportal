package com.example.jobportal.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobResponseDTO {

    private Long id;
    private String title;
    private String description;
    private String location;
    private String companyName;
    private Double salary;
    private String jobType;

}
