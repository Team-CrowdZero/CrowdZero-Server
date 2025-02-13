package com.crowdzero.crowdzero_sever.repository;

import com.crowdzero.crowdzero_sever.domain.Place;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Integer> {
    @Query("SELECT p from Place p")
    List<Place> findAll();
}