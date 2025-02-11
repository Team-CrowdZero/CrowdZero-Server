package com.crowdzero.crowdzero_sever.accidentApi.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccidentRepository extends JpaRepository<Accident, Long> {
    @Query("SELECT a FROM Accident a WHERE a.area.id = :areaId AND a.acdntOccrDt LIKE CONCAT(:currentDate, '%')")
    List<Accident> findByAreaIdAndCurrentDate(@Param("areaId") Long areaId, @Param("currentDate") String currentDate);
}