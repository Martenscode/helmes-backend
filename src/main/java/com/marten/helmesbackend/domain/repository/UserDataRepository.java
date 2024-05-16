package com.marten.helmesbackend.domain.repository;

import com.marten.helmesbackend.domain.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserDataRepository extends JpaRepository<UserData, Integer> {

    UserData findByUuid(UUID uuid);

}
