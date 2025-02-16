package com.crowdzero.crowdzero_sever.assemblyApi.repository;

import com.crowdzero.crowdzero_sever.assemblyApi.domain.Assembly;
import com.crowdzero.crowdzero_sever.assemblyApi.dto.AssemblyResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssemblyRepository extends JpaRepository<Assembly, Long> {
    List<AssemblyResponseDto> findByDate(String date); // 특정 날짜의 집회 데이터 조회
}
