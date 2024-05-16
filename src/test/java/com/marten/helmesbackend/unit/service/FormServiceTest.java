package com.marten.helmesbackend.unit.service;

import com.marten.helmesbackend.common.dto.UserDataDTO;
import com.marten.helmesbackend.common.exceptions.InvalidUserDataException;
import com.marten.helmesbackend.domain.entity.Sector;
import com.marten.helmesbackend.domain.entity.UserData;
import com.marten.helmesbackend.domain.repository.SectorRepository;
import com.marten.helmesbackend.domain.repository.UserDataRepository;
import com.marten.helmesbackend.service.FormService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//review so the format of assertERquals is : expected - actual

//Not loading whole context with @SpringBootTest to speed up tests.
@ExtendWith(MockitoExtension.class)
class FormServiceTest {

    @Mock
    private SectorRepository sectorRepository;

    @Mock
    private UserDataRepository userDataRepository;

    @InjectMocks
    private FormService formService;

    @Test
    void Should_ReturnListOfSectors_When_SectorsFound() {
        //Arrange
        List<Sector> expectedSectorList = List.of(mock(Sector.class));
        when(sectorRepository.findAllByParentNull(any(Sort.class))).thenReturn(expectedSectorList);

        //Act
        List<Sector> sectorList = formService.getAllSectors();

        //Assert
        assertEquals(expectedSectorList, sectorList);
    }

    @Test
    void Should_ReturnEmptyList_When_NoSectorsFound() {
        List<Sector> expectedSectorList = List.of();
        when(sectorRepository.findAllByParentNull(any(Sort.class))).thenReturn(expectedSectorList);

        List<Sector> sectorList = formService.getAllSectors();

        assertEquals(expectedSectorList, sectorList);
    }

    @Test
    void Should_UpdateExistingData_When_UUIDIsNotNull_Given_UserDataExists() {
        setUpSectorsInDB();
        UserData userDataInDB = UserData.builder()
                .uuid(UUID.randomUUID())
                .name("name")
                .sectorIds(List.of(1,2))
                .agreeToTerms(true)
                .build();
        when(userDataRepository.findByUuid(any(UUID.class))).thenReturn(userDataInDB);
        UserDataDTO userRequestWithExistingUUID = UserDataDTO.builder()
                .uuid(userDataInDB.getUuid())
                .name("newname")
                .sectorIds(List.of(3))
                .terms(true)
                .build();
        UserData savedData = mapUserDataDTOToUserData(userRequestWithExistingUUID);
        when(userDataRepository.save(any(UserData.class))).thenReturn(savedData);

        UserDataDTO responseDTO = formService.submitUserData(userRequestWithExistingUUID);

        assertEquals(savedData.getUuid(), responseDTO.getUuid());
        assertEquals(savedData.getName(), responseDTO.getName());
        assertEquals(savedData.getSectorIds(), responseDTO.getSectorIds());
        assertEquals(savedData.isAgreeToTerms(), responseDTO.isTerms());
    }

    @Test
    void Should_NotUpdateExistingData_When_UUIDIsNotNull_Given_UserDataDoesNotExist() {
        setUpSectorsInDB();
        when(userDataRepository.findByUuid(any(UUID.class))).thenReturn(null);
        UserDataDTO userRequestWithUuid = UserDataDTO.builder()
                .uuid(UUID.randomUUID())
                .name("newname")
                .sectorIds(List.of(3))
                .terms(true)
                .build();
        UserData savedData = UserData.builder()
                .uuid(UUID.randomUUID())
                .name(userRequestWithUuid.getName())
                .sectorIds(userRequestWithUuid.getSectorIds())
                .agreeToTerms(userRequestWithUuid.isTerms())
                .build();
        when(userDataRepository.save(any(UserData.class))).thenReturn(savedData);

        UserDataDTO responseDTO = formService.submitUserData(userRequestWithUuid);

        assertNotSame(responseDTO.getUuid(), userRequestWithUuid.getUuid());
        assertEquals(responseDTO.getName(), userRequestWithUuid.getName());
        assertEquals(responseDTO.getSectorIds(), userRequestWithUuid.getSectorIds());
        assertEquals(responseDTO.isTerms(), userRequestWithUuid.isTerms());
    }

    @Test
    void Should_SaveNewData_When_UUIDIsNull() {
        setUpSectorsInDB();
        UserDataDTO userRequest = UserDataDTO.builder()
                .uuid(null)
                .name("name")
                .sectorIds(List.of(1,2,3))
                .terms(true)
                .build();
        UserData savedData = UserData.builder()
                .uuid(UUID.randomUUID())
                .name(userRequest.getName())
                .sectorIds(userRequest.getSectorIds())
                .agreeToTerms(userRequest.isTerms())
                .build();
        when(userDataRepository.save(any(UserData.class))).thenReturn(savedData);

        UserDataDTO responseDTO = formService.submitUserData(userRequest);

        assertNotEquals(userRequest.getUuid(), responseDTO.getUuid());
        assertEquals(userRequest.getName(), responseDTO.getName());
        assertEquals(userRequest.getSectorIds(), responseDTO.getSectorIds());
        assertEquals(userRequest.isTerms(), responseDTO.isTerms());
        verify(userDataRepository, never()).findByUuid(any(UUID.class));
    }

    @Test
    void Should_ThrowInvalidUserDataException_When_NoSectorsFoundInRepository_Given_EmptyList() {
        when(sectorRepository.findAllIds()).thenReturn(List.of());

        assertThrows(InvalidUserDataException.class, () -> {
            formService.submitUserData(mock(UserDataDTO.class));
        });
    }

    @Test
    void Should_ThrowInvalidUserDataException_When_UserEntersNonExistingSector() {
        setUpSectorsInDB();
        UserDataDTO userRequestWithNonExistingSectors = mock(UserDataDTO.class);
        when(userRequestWithNonExistingSectors.getSectorIds()).thenReturn(List.of(99999));

        assertThrows(InvalidUserDataException.class, () -> {
            formService.submitUserData(userRequestWithNonExistingSectors);
        });
    }

    private UserData mapUserDataDTOToUserData(UserDataDTO userDataDTO) {
        return UserData.builder()
                .uuid(userDataDTO.getUuid())
                .name(userDataDTO.getName())
                .sectorIds(userDataDTO.getSectorIds())
                .agreeToTerms(userDataDTO.isTerms())
                .build();
    }

    private void setUpSectorsInDB() {
        List<Integer> sectorIdsInDB = List.of(1,2,3,4,5);
        when(sectorRepository.findAllIds()).thenReturn(sectorIdsInDB);
    }

}
