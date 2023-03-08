package embedika.code.controller;

import embedika.code.dto.CarDto;
import embedika.code.model.Car;
import embedika.code.service.CarServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping("${base-url}" + "/cars")
public class CarController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarController.class);

    private final CarServiceImpl carService;

    @Operation(summary = "Get all cars")
    @ApiResponse(responseCode = "200", description = "Successfully load cars")
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public ModelAndView getCars(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAtDesc") String sortBy,
            HttpSession session
    ) {
        ModelAndView mv = new ModelAndView("urls/cars/index.html");
        List<Car> cars = carService.getCars(sortBy);
        LOGGER.info("CARS: " + cars);

        int totalElements = cars.size();
        int start = page * size;
        int end = Math.min((start + size), totalElements);
        Page<Car> pageResult = new PageImpl<>(cars.subList(start, end), PageRequest.of(page, size), totalElements);

        mv.addObject("sortBy", sortBy);
        mv.addObject("cars", pageResult);
        String flash = (String) session.getAttribute("flash");
        String flashType = (String) session.getAttribute("flashType");
        if (flash != null && flashType != null) {
            mv.addObject("flash", flash);
            mv.addObject("flashType", flashType);
            session.removeAttribute("flash");
            session.removeAttribute("flashType");
        }
        return mv;
    }


    /**
     * Без JS не разобрался, как сразу отправлять JSON через &lt;form&gt; в html
     **/
    @Operation(summary = "Post new car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car successfully added"),
            @ApiResponse(responseCode = "302", description = "Car already exists"),
            @ApiResponse(responseCode = "400", description = "Car data is not valid")
    })
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView createCar(
            @Parameter(schema = @Schema(implementation = CarDto.class))
            @Valid
            CarDto carDto,
            HttpSession session
    ) {
        LOGGER.info("CARDTO: " + carDto);
        ModelAndView mv = new ModelAndView("redirect:/api/cars");
        if (!carService.isCarPresentByPlateNumber(carDto.getPlateNumber())) {
            carService.addCar(carDto);
            mv.setStatus(HttpStatus.SEE_OTHER);
            session.setAttribute("flash", "Car successfully added");
            session.setAttribute("flashType", "success");
        } else {
            mv.setStatus(HttpStatus.FOUND);
            session.setAttribute("flash", "Car already exists");
            session.setAttribute("flashType", "info");
        }
        return mv;
    }

    @Operation(summary = "Delete car")
    @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Car successfully deleted"),
                @ApiResponse(responseCode = "404", description = "Car with such id not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCar(
            @PathVariable String id,
            HttpSession session
    ) {
        LOGGER.info("Given car Id is: " + id);
        Long carId = Long.parseLong(id);
        carService.deleteCar(carId);
        session.setAttribute("flash", "Car successfully deleted");
        session.setAttribute("flashType", "success");
    }

    @Operation(summary = "Get database stats")
    @ApiResponse(responseCode = "200", description = "Stats successfully loaded")
    @GetMapping("/statistics")
    @ResponseStatus(value = HttpStatus.OK)
    public ModelAndView getStatistics() {
        List<String> stats = carService.getDbStats();
        return new ModelAndView("urls/cars/statistics.html")
                .addObject("stats", stats);
    }
}
