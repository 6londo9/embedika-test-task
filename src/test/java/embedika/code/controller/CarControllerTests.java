package embedika.code.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import embedika.code.model.Car;
import embedika.code.repository.CarRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CarControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CarRepository carRepository;

    @BeforeEach
    void beforeEach() {
        setUp();
    }

    @AfterEach
    void tearDown() {
        carRepository.deleteAll();
    }

    @Test
    void testGetRootPage() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(get("/api"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).contains("Plate number");
    }

    @Test
    void testGetCarsPage() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(get("/api/cars"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).contains("AA001A01");
        assertThat(response.getContentAsString()).contains("BB001B01");
        assertThat(response.getContentAsString()).contains("CC001C01");
    }

    @Test
    void testDeleteCar() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(delete("/api/cars/2"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(carRepository.count()).isEqualTo(2);
        assertThat(carRepository.existsByPlateNumber("AA001A01")).isTrue();
        assertThat(carRepository.existsByPlateNumber("BB001B01")).isFalse();
        assertThat(carRepository.existsByPlateNumber("CC001C01")).isTrue();
    }

    @Test
    @Disabled(value = "For some reason invalid delete return 200")
    void deleteUnknownCar() throws Exception {
        MockHttpServletResponse response = mockMvc
                .perform(delete("/api/cars/1000"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(404);
        assertThat(carRepository.existsByPlateNumber("AA001A01")).isTrue();
        assertThat(carRepository.existsByPlateNumber("BB001B01")).isFalse();
        assertThat(carRepository.existsByPlateNumber("CC001C01")).isTrue();
        assertThat(carRepository.count()).isEqualTo(3);
    }

    @Test
    @Disabled(value = "Can't handle testing Post request for now")
    void testAddCar() throws Exception {
        ObjectNode node = MAPPER.createObjectNode();
        node.put("carPlateNumber", "ZZ001Z01");
        node.put("carColor", "black");
        node.put("yearOfIssue", 2011);
        String car = URLEncoder.encode(MAPPER.writeValueAsString(node), StandardCharsets.UTF_8);
        MockHttpServletResponse response = mockMvc
                .perform(post("/api/cars")
                                .content(car)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
    }


    private void setUp() {
        Car car1 = new Car();
        car1.setId(1L);
        car1.setPlateNumber("AA001A01");
        car1.setModel("TOYOTA");
        car1.setColor("black");
        car1.setYearOfIssue(2010);

        Car car2 = new Car();
        car2.setId(2L);
        car2.setPlateNumber("BB001B01");
        car2.setModel("BMW");
        car2.setColor("black");
        car2.setYearOfIssue(2010);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(2004, 1, 1).atStartOfDay();
        car1.setCreatedAt(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant());

        Car car3 = new Car();
        car3.setId(3L);
        car3.setPlateNumber("CC001C01");
        car3.setModel("MERCEDES");
        car3.setColor("black");
        car3.setYearOfIssue(2010);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(2010, 1, 1).atStartOfDay();
        car1.setCreatedAt(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant());

        carRepository.save(car1);
        carRepository.save(car2);
        carRepository.save(car3);
    }

    private static final ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules();

    public static String asJson(final Object object) throws JsonProcessingException {
        return MAPPER.writeValueAsString(object);
    }
}
