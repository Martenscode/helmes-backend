package com.marten.helmesbackend.domain.repository;

import com.marten.helmesbackend.domain.entity.Sector;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SectorRepository extends JpaRepository<Sector, Integer> {

    List<Sector> findAllByParentNull(Sort sort);

    @Query("SELECT s.id FROM Sector s")
    List<Integer> findAllIds();

}
