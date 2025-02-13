package com.crowdzero.crowdzero_sever.populationApi.service;

import com.crowdzero.crowdzero_sever.domain.Place;
import com.crowdzero.crowdzero_sever.populationApi.domain.Population;
import com.crowdzero.crowdzero_sever.populationApi.dto.PopulationResponseDto;
import com.crowdzero.crowdzero_sever.populationApi.repository.PopulationRepository;
import com.crowdzero.crowdzero_sever.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PopulationService {
    private final PopulationRepository populationRepository;
    private final PlaceRepository placeRepository;

    // ID로 인구 데이터 조회
    public PopulationResponseDto findPopulationById(Integer areaId) {
        Population population = populationRepository.findById(areaId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 Population 데이터가 없습니다."));
        return PopulationResponseDto.from(population);
    }

    // 새 인구 데이터 저장 (전체 Population 데이터 개수가 5개 넘으면 가장 오래된 데이터 삭제)
    @Transactional
    public PopulationResponseDto savePopulation(PopulationResponseDto populationResponseDto) {
        Place place = placeRepository.findById(populationResponseDto.getAreaId())
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 Place가 존재하지 않습니다."));

        // 새로운 Population 데이터 생성
        Population population = Population.builder()
                .areaCongestLvl(populationResponseDto.getAreaCongestLvl())
                .areaCongestMsg(populationResponseDto.getAreaCongestMsg())
                .areaPpltnMax(populationResponseDto.getAreaPpltnMax())
                .areaPpltnMin(populationResponseDto.getAreaPpltnMin())
                .ppltnTime(populationResponseDto.getPpltnTime())
                .place(place)
                .build();

        // 데이터 저장
        Population savedPopulation = populationRepository.save(population);

        // 전체 Population 데이터 개수 확인
        long count = populationRepository.count();
        if (count > 5) {
            // 가장 오래된 데이터 삭제
            populationRepository.findOldestRecord()
                    .ifPresent(oldest -> populationRepository.deleteById(oldest.getId()));
        }

        return PopulationResponseDto.from(savedPopulation);
    }
}
