package com.crowdzero.crowdzero_sever.populationApi.repository;

import com.crowdzero.crowdzero_sever.populationApi.domain.population;
import org.springframework.data.jpa.repository.JpaRepository;

public interface populationRepository extends JpaRepository<population, Long> {
}
