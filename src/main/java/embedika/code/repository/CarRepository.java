package embedika.code.repository;

import embedika.code.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("SELECT c FROM Car c ORDER BY "
            + "SUBSTR(c.plateNumber, 1, 2), "
            + "CAST(SUBSTR(c.plateNumber, 3, 3) AS integer), "
            + "SUBSTR(c.plateNumber, 6, 1), "
            + "CAST(SUBSTR(c.plateNumber, 7) AS integer)")
    List<Car> findAllSortedByPlateNumber();
    List<Car> findAllByOrderByCreatedAtAsc();
    List<Car> findAllByOrderByCreatedAtDesc();
    List<Car> findAllByOrderByModel();
    List<Car> findAllByOrderByColor();
    List<Car> findAllByOrderByYearOfIssue();
    List<Car> findAllByOrderByYearOfIssueDesc();
    boolean existsById(Long carId);
    boolean existsByPlateNumber(String carPlateNumber);
}
