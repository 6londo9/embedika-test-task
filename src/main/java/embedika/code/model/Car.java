package embedika.code.model;

import embedika.code.validator.CarPlateNumber;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "cars")
@Getter
@Setter
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @CarPlateNumber
    @Column(unique = true)
    private String carPlateNumber;
    @NotBlank
    private String carColor;
    @NotNull
    @Digits(integer = 4, fraction = 0)
    private Integer yearOfIssue;
    @CreationTimestamp
    private Instant createdAt;
}
