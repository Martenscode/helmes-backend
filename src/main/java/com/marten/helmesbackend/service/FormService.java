package com.marten.helmesbackend.service;

import com.marten.helmesbackend.common.dto.UserDataDTO;
import com.marten.helmesbackend.common.exceptions.InvalidUserDataException;
import com.marten.helmesbackend.domain.entity.Sector;
import com.marten.helmesbackend.domain.entity.UserData;
import com.marten.helmesbackend.domain.repository.SectorRepository;
import com.marten.helmesbackend.domain.repository.UserDataRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class FormService {

    private SectorRepository sectorRepository;
    private UserDataRepository userDataRepository;

    public List<Sector> getAllSectors() {
        return sectorRepository.findAllByParentNull(Sort.by("name").ascending());
    }

    public UserDataDTO submitUserData(UserDataDTO userDataDTO) {
        validateSectorIds(userDataDTO.getSectorIds());

        if (userDataDTO.getUuid() != null) {
            UserData existingUserData = userDataRepository.findByUuid(userDataDTO.getUuid());
            if (existingUserData != null) {
                existingUserData.setName(userDataDTO.getName());
                existingUserData.setSectorIds(userDataDTO.getSectorIds());
                existingUserData.setAgreeToTerms(userDataDTO.isTerms());

                UserData savedUser = userDataRepository.save(existingUserData);
                log.warn("Updated user data for UUID={}", savedUser.getUuid());
                return mapUserDataToDTO(savedUser);
            } else {
                log.warn("User submitted data with UUID which did not exist in DB; UUID={}", userDataDTO.getUuid());
            }
        }

        UserData userData = UserData.builder()
                .uuid(UUID.randomUUID())
                .name(userDataDTO.getName())
                .sectorIds(userDataDTO.getSectorIds())
                .agreeToTerms(userDataDTO.isTerms())
                .build();
        UserData savedUser = userDataRepository.save(userData);
        log.warn("Saved user data for UUID={}", savedUser.getUuid());
        return mapUserDataToDTO(savedUser);
    }

    private UserDataDTO mapUserDataToDTO(UserData userData) {
        return UserDataDTO.builder()
                .uuid(userData.getUuid())
                .name(userData.getName())
                .sectorIds(userData.getSectorIds())
                .terms(userData.isAgreeToTerms())
                .build();
    }

    /**
     * Because database structure is de-normalized (these IDs are without FK) we instead move some validation logic to Java which ensures that tbe user enters only acceptable IDs.
     * I'm not a fan of this but as far as I'm concerned, we don't need to specify exact ID which was failing for err msg
     * because the only way this exception is triggered, is if someone tinkers with requests (sectors are loaded from database so front-end is always up-to-date) **/
    private void validateSectorIds(List<Integer> sectorIds) {
        Set<Integer> existingSectorIds = new HashSet<>(sectorRepository.findAllIds());
        if (sectorIds.isEmpty()) {
            log.warn("Sector IDs cannot be empty");
            throw new InvalidUserDataException();
        }
        Set<Integer> validSectorIds = sectorIds.stream()
                .filter(existingSectorIds::contains)
                .collect(Collectors.toSet());
        if (validSectorIds.size() != sectorIds.size()) {
            log.warn("One or more sector IDs are invalid");
            throw new InvalidUserDataException();
        }
    }

}
