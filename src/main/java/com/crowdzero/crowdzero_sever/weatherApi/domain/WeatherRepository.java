package com.crowdzero.crowdzero_sever.weatherApi.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {
    // 장소 아이디로 날씨 찾기
    @Query("SELECT w FROM Weather w WHERE w.area.id = :areaId")
    Optional<Weather> findByAreaId(Long areaId);
}