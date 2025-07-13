package com.example.jobportal.service;

import com.example.jobportal.dto.CompanyRequestDTO;
import com.example.jobportal.dto.CompanyResponseDTO;
import com.example.jobportal.exception.ResourceNotFoundException;
import com.example.jobportal.model.CompanyEntity;
import com.example.jobportal.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService{

    private final CompanyRepository companyRepository;

    @Override
    public CompanyResponseDTO createCompany(CompanyRequestDTO companyRequestDTO) {

        CompanyEntity company = CompanyEntity.builder()
                .name(companyRequestDTO.getName())
                .description(companyRequestDTO.getDescription())
                .website(companyRequestDTO.getWebsite())
                .location(companyRequestDTO.getLocation())
                .build();

        return mapToDTO(companyRepository.save(company));

    }



    @Override
    public List<CompanyResponseDTO> getAllCompanies() {

        return companyRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

       // return List.of();
    }

    @Override
    public CompanyResponseDTO getCompanyById(Long id) {

        CompanyEntity company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id "+id));

        return mapToDTO(company);
    }

    @Override
    public CompanyResponseDTO updateCompany(Long id, CompanyRequestDTO companyRequestDTO) {

        CompanyEntity company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id "+id));

        company.setName(companyRequestDTO.getName());
        company.setDescription(companyRequestDTO.getDescription());
        company.setWebsite(companyRequestDTO.getWebsite());
        company.setLocation(companyRequestDTO.getLocation());

        return mapToDTO(companyRepository.save(company));
    }

    @Override
    public void deleteCompany(Long id) {

        CompanyEntity company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id"+ id));

        companyRepository.delete(company);

    }

    private CompanyResponseDTO mapToDTO(CompanyEntity company) {

        return CompanyResponseDTO.builder()
                .id(company.getId())
                .name(company.getName())
                .description(company.getDescription())
                .website(company.getWebsite())
                .location(company.getLocation())
                .build();

    }
}
