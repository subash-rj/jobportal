package com.example.jobportal.service;

import com.example.jobportal.dto.CompanyRequestDTO;
import com.example.jobportal.dto.CompanyResponseDTO;

import java.util.List;

public interface CompanyService {

    CompanyResponseDTO createCompany(CompanyRequestDTO companyRequestDTO);
    List<CompanyResponseDTO> getAllCompanies();
    CompanyResponseDTO getCompanyById(Long id);
    CompanyResponseDTO updateCompany(Long id,CompanyRequestDTO companyRequestDTO);
    void deleteCompany(Long id);

}
