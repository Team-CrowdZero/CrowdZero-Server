package com.crowdzero.crowdzero_sever.config;

import com.crowdzero.crowdzero_sever.domain.Place;
import com.crowdzero.crowdzero_sever.repository.PlaceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class PlaceDataInitializer implements CommandLineRunner {
    private final PlaceRepository placeRepository;

    public PlaceDataInitializer(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Override
    public void run(String... args) {
        List<Place> places = Arrays.asList(
                // TODO: 프론트에서 imageUrl 받기
                new Place(1, "강남역", "POI014", ""),
                new Place(2, "광화문 광장", "POI088", ""),
                new Place(3, "삼각지역", "POI030", ""),
                new Place(4, "서울역", "POI033", ""),
                new Place(5, "여의도", "POI072", "")
        );

        // 초기 데이터가 없는 경우에만 저장
        if (placeRepository.count() == 0) {
            placeRepository.saveAll(places);
            System.out.println("장소 초기 데이터가 성공적으로 저장되었습니다.");
        }
    }
}
