package com.crowdzero.crowdzero_sever.populationApi.config;

import com.crowdzero.crowdzero_sever.domain.Place;
import com.crowdzero.crowdzero_sever.populationApi.domain.Population;
import com.crowdzero.crowdzero_sever.populationApi.repository.PopulationRepository;
import com.crowdzero.crowdzero_sever.repository.PlaceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@DependsOn("placeDataInitializer") // PlaceDataInitializer가 먼저 실행되도록 설정
public class PopulationDataInitializer implements CommandLineRunner {
    private final PopulationRepository populationRepository;
    private final PlaceRepository placeRepository;

    public PopulationDataInitializer(PopulationRepository populationRepository, PlaceRepository placeRepository) {
        this.populationRepository = populationRepository;
        this.placeRepository = placeRepository;
    }

    @Override
    public void run(String... args) {
        try {
            Thread.sleep(3000); // 데이터 저장 딜레이 (3초)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 기존 데이터가 없는 경우만 저장
        if (populationRepository.count() == 0) {
            Optional<Place> gangnamStation = placeRepository.findById(1);
            Optional<Place> seoulStation2 = placeRepository.findById(2);
            Optional<Place> seoulStation3 = placeRepository.findById(3);
            Optional<Place> seoulStation4 = placeRepository.findById(4);
            Optional<Place> seoulStation5 = placeRepository.findById(5);

            if (gangnamStation.isPresent() && seoulStation2.isPresent() &&
                    seoulStation3.isPresent() && seoulStation4.isPresent() && seoulStation5.isPresent()) {
                List<Population> populations = List.of(
                        new Population(null, "혼잡", "매우 혼잡한 상태", 1000, 500, "2025-02-12 18:00", gangnamStation.get()),
                        new Population(null, "보통", "보통 수준의 인구 밀집1", 800, 400, "2025-02-12 18:00", seoulStation2.get()),
                        new Population(null, "여유", "보통 수준의 인구 밀집2", 800, 400, "2025-02-12 18:00", seoulStation3.get()),
                        new Population(null, "여유", "보통 수준의 인구 밀집3", 800, 400, "2025-02-12 18:00", seoulStation4.get()),
                        new Population(null, "혼잡", "보통 수준의 인구 밀집4", 800, 400, "2025-02-12 18:00", seoulStation5.get())
                );

                populationRepository.saveAll(populations);
                System.out.println("인구 초기 데이터가 성공적으로 저장되었습니다.");
            } else {
                System.out.println("초기 데이터를 추가할 장소 정보가 부족합니다.");
            }
        }
    }
}
