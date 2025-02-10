package com.crowdzero.crowdzero_sever.repository;

import com.crowdzero.crowdzero_sever.domain.Place;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Integer> {
}