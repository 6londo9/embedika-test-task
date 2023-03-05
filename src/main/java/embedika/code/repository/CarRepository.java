package embedika.code.repository;

import embedika.code.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("SELECT c FROM Car c ORDER BY "
            + "SUBSTR(c.carPlateNumber, 1, 2), "
            + "CAST(SUBSTR(c.carPlateNumber, 3, 3) AS integer), "
            + "SUBSTR(c.carPlateNumber, 6, 1), "
            + "CAST(SUBSTR(c.carPlateNumber, 7) AS integer)")
    List<Car> findAllSortedByCarPlateNumber();
    List<Car> findAllByOrderByCreatedAtAsc();
    List<Car> findAllByOrderByCreatedAtDesc();
    List<Car> findAllByOrderByCarColor();
    List<Car> findAllByOrderByYearOfIssue();
    List<Car> findAllByOrderByYearOfIssueDesc();
    boolean existsById(Long carId);
    boolean existsByCarPlateNumber(String carPlateNumber);
}
