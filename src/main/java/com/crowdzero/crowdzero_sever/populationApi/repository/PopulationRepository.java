package com.crowdzero.crowdzero_sever.populationApi.repository;

import com.crowdzero.crowdzero_sever.domain.Place;
import com.crowdzero.crowdzero_sever.populationApi.domain.Population;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface PopulationRepository extends JpaRepository<Population, Integer> {

    Optional<Population> findByPlace(Place place);

    // 가장 오래된 데이터 조회 (전체 Population 테이블에서)
    @Query("SELECT p FROM Population p ORDER BY p.ppltnTime ASC LIMIT 1")
    Optional<Population> findOldestRecord();

    // 전체 데이터 개수 조회
    @Query("SELECT COUNT(p) FROM Population p")
    long count();

    // 특정 ID의 데이터 삭제
    @Modifying
    @Transactional
    @Query("DELETE FROM Population p WHERE p.id = :id")
    void deleteById(@Param("id") Integer id);
}
