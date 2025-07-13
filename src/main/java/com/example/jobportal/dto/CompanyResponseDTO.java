package com.example.jobportal.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponseDTO {

    private Long id;

    private String name;

    private String description;

    private String website;

    private String location;

}
