package com.marten.helmesbackend.unit.controller;

import com.marten.helmesbackend.common.dto.UserDataDTO;
import com.marten.helmesbackend.controller.FormController;
import com.marten.helmesbackend.domain.entity.Sector;
import com.marten.helmesbackend.service.FormService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class FormControllerTest {

    @MockBean
    private FormService formService;

    @Autowired
    private FormController formController;

    @Test
    void Should_ReturnSectorsWithStatusOK_When_GetEndpointCalled() {
        List<Sector> expectedResponseBody = List.of(mock(Sector.class));
        when(formService.getAllSectors()).thenReturn(expectedResponseBody);

        ResponseEntity<List<Sector>> response = formController.getAllSectors();

        assertEquals(expectedResponseBody, response.getBody());
        assertEquals(HttpStatus.OK, HttpStatus.resolve(response.getStatusCode().value()));
    }

    @Test
    void Should_ReturnUserDataDTOWithStatusOK_When_PostEndpointCalled() {
        UserDataDTO userDataDTO = UserDataDTO.builder()
                .uuid(UUID.randomUUID())
                .name("name")
                .sectorIds(List.of(1))
                .terms(true)
                .build();
        when(formService.submitUserData(any(UserDataDTO.class))).thenReturn(userDataDTO);

        ResponseEntity<UserDataDTO> response = formController.submitUserData(userDataDTO);

        assertEquals(userDataDTO, response.getBody());
        assertEquals(HttpStatus.OK, HttpStatus.resolve(response.getStatusCode().value()));
    }

}
