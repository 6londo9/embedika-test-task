package embedika.code.service;

import embedika.code.dto.CarDto;
import embedika.code.model.Car;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Optional;

public interface CarService {

    Car addCar(CarDto carDto);
    List<Car> getCars(String param);
    Optional<Car> getCarById(Long id);
    boolean isCarPresentByCarPlateNumber(@NotBlank @Size String carPlateNumber);
    void deleteCar(Long carId);
    List<String> getDbStats();
}
