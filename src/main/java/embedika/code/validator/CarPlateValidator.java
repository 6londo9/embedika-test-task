package embedika.code.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class CarPlateValidator implements ConstraintValidator<CarPlateNumber, String> {
    private static final Pattern CAR_PLATE_NUMBER_PATTERN =
            Pattern.compile("[A-Z]{2}[0-9]{3}[A-Z][0-9]{2,3}");
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return CAR_PLATE_NUMBER_PATTERN.matcher(value).matches();
    }
}
