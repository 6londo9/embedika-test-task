package embedika.code.dto;

import embedika.code.validator.CarPlateNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CarDto {
    @NotBlank
    @CarPlateNumber
    private String plateNumber;
    @NotBlank
    private String model;
    @NotBlank
    private String color;
    @NotNull
    @Digits(integer = 4, fraction = 0)
    private Integer yearOfIssue;
}
