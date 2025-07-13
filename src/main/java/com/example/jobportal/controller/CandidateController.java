package com.example.jobportal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @GetMapping("/dashboard")
    public ResponseEntity<String> getCandidateProfile(){
        return ResponseEntity.ok("Welcome Candidate");
    }

}
