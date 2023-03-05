package embedika.code.controller;

import embedika.code.repository.CarRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
public class CarControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CarRepository carRepository;

    @AfterEach
    void afterEach() {
        carRepository.deleteAll();
    }

//    @Test
//    void createCar() throws Exception {
//        assertEquals(0, carRepository.count());
//
//        MockHttpServletResponse response = (MockHttpServletResponse) mockMvc.perform(
//                (RequestBuilder) post("/api/url")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .queryParam("carPlateNumber", "AA100A01")
//                .queryParam("carColor", "black")
//                .queryParam("yearOfIssue", "2000")
//        ).andExpect(status().isOk());
//    }
}
