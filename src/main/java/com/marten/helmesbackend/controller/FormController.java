package com.marten.helmesbackend.controller;

import com.marten.helmesbackend.common.dto.UserDataDTO;
import com.marten.helmesbackend.domain.entity.Sector;
import com.marten.helmesbackend.service.FormService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/form")
public class FormController {

    private FormService formService;

    //Caching should be implemented on this endpoint
    @GetMapping
    public ResponseEntity<List<Sector>> getAllSectors() {
        return ResponseEntity.ok().body(formService.getAllSectors());
    }

    @PostMapping
    public ResponseEntity<UserDataDTO> submitUserData(@Valid @RequestBody UserDataDTO userDataDTO) {
        return ResponseEntity.ok().body(formService.submitUserData(userDataDTO));
    }

}
