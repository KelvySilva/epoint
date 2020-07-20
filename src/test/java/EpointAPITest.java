import br.com.epoint.EpointApplicationStart;
import br.com.epoint.domain.Employee;
import br.com.epoint.domain.Point;
import br.com.epoint.repository.PointRepository;
import br.com.epoint.service.PointService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpointApplicationStart.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc()
@TestPropertySource(locations="classpath:apllication-test.yml")
public class EpointAPITest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PointService service;

    @LocalServerPort
    int port;

    @MockBean
    private PointRepository repository;

    @TestConfiguration
    static class Config{

        @Bean
        public RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder()
                    .basicAuthentication("rhumanos2","senha123");
        }
    }

    @Test
    public void savePointShouldReturnError(){
        Point point = new Point();
        Employee employee = new Employee();
        employee.setType(Employee.TYPE.DEV);
        employee.setActive(true);
        employee.setAdmin(false);
        employee.setCode(6564L);
        employee.setBlocked(false);

        point.setType(Point.POINT_TYPE.ENTRADA);
        point.setEmployee(employee);
        point.setPointDate(LocalDate.parse("2020-07-20"));
        point.setPointTime(LocalTime.parse("08:00:00"));


        BDDMockito
                .when(service.saveOne(point))
                .thenReturn(point);
        ResponseEntity<String> response = restTemplate.postForEntity("point/admin", point, String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }
}
