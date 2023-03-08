package embedika.code.service;

import embedika.code.dto.CarDto;
import embedika.code.model.Car;
import embedika.code.repository.CarRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private static final String CAR_PLATE = "carPlate";
    private static final String YEAR_OF_ISSUE = "yearOfIssue";
    private static final String YEAR_OF_ISSUE_DESC = "yearOfIssueDesc";
    private static final String COLOR = "color";
    private static final String CREATED_AT = "createdAt";
    private static final String CREATED_AT_DESC = "createdAtDesc";
    private static final String MODEL = "model";
    @Override
    public Car addCar(CarDto carDto) {
        return carRepository.save(
                merge(new Car(), carDto)
        );
    }

    @Override
    public boolean isCarPresentByPlateNumber(@NotBlank @Size String carPlateNumber) {
        return carRepository.existsByPlateNumber(carPlateNumber);
    }

    @Override
    public List<Car> getCars(String param) {
        switch (param) {
            case CAR_PLATE -> {
                return getCarsByCarPlateNumber();
            }
            case YEAR_OF_ISSUE -> {
                return getCarsByYearOfIssue();
            }
            case YEAR_OF_ISSUE_DESC -> {
                return getCarsByYearOfIssueDesc();
            }
            case COLOR -> {
                return getCarsByColor();
            }
            case CREATED_AT -> {
                return getCarsAsc();
            }
            case CREATED_AT_DESC -> {
                return getCarsDesc();
            }
            case MODEL -> {
                return getCarsByCarModel();
            }
            default -> throw new RuntimeException("Sorting with param: " + param + " is not available");
        }
    }

    @Override
    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    private List<Car> getCarsDesc() {
        return carRepository.findAllByOrderByCreatedAtDesc();
    }
    private List<Car> getCarsAsc() {
        return carRepository.findAllByOrderByCreatedAtAsc();
    }

    private List<Car> getCarsByColor() {
        return carRepository.findAllByOrderByColor();
    }

    private List<Car> getCarsByYearOfIssue() {
        return carRepository.findAllByOrderByYearOfIssue();
    }
    private List<Car> getCarsByYearOfIssueDesc() {
        return carRepository.findAllByOrderByYearOfIssueDesc();
    }

    private List<Car> getCarsByCarPlateNumber() {
        return carRepository.findAllSortedByPlateNumber();
    }
    private List<Car> getCarsByCarModel() {
        return carRepository.findAllByOrderByModel();
    }


    @Override
    public void deleteCar(Long carId) {
        carRepository.deleteById(carId);
    }

    @Override
    public List<String> getDbStats() {
        String count = String.valueOf(carRepository.count());
        String firstAdded = carRepository.findAllByOrderByCreatedAtAsc().isEmpty()
                ? "" : carRepository.findAllByOrderByCreatedAtAsc().get(0).getPlateNumber();
        String lastAdded = carRepository.findAllByOrderByCreatedAtDesc().isEmpty()
                ? "" : carRepository.findAllByOrderByCreatedAtDesc().get(0).getPlateNumber();
        return List.of(count, firstAdded, lastAdded);
    }


    private Car merge(Car car, CarDto carDto) {
        car.setModel(carDto.getModel().toUpperCase());
        car.setColor(carDto.getColor().toLowerCase());
        car.setPlateNumber(carDto.getPlateNumber().toUpperCase());
        car.setYearOfIssue(carDto.getYearOfIssue());
        return car;
    }
}
