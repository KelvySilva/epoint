import br.com.epoint.EpointApplicationStart;
import br.com.epoint.domain.Employee;
import br.com.epoint.domain.Point;
import br.com.epoint.repository.EmployeeRepository;
import br.com.epoint.repository.PointRepository;
import br.com.epoint.service.EmployeeService;
import br.com.epoint.service.PointService;
import br.com.epoint.util.PasswordEncoder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(properties = "apllication-test.yml",classes = EpointApplicationStart.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc()
public class EpointAPITest {


    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PointService service;
    @Autowired
    private EmployeeService employeeService;


    @LocalServerPort
    int port;

    @MockBean
    private PointRepository repository;

    @MockBean
    private EmployeeRepository employeeRepository;

//    @TestConfiguration
//    static class Config{
//
//        @Bean
//        public RestTemplateBuilder restTemplateBuilder() {
//            return new RestTemplateBuilder()
//                    .basicAuthentication("rhumanos","aj[lo12po");
//        }
//    }

    @Test
    public void savePointShouldReturnError(){
        restTemplate = restTemplate.withBasicAuth("rhumanos", "aj[lo12po");
        restTemplate.getRestTemplate().setInterceptors(Collections.singletonList(new RequestResponseLoggingInterceptor()));
        Point point = new Point();
        Employee employee = new Employee();
        employee.setCode(6564L);

        point.setEmployee(employee);
        System.out.println("Point: "+point);
        point.setPointDate(LocalDate.parse("2020-07-21"));
        point.setPointTime(LocalTime.parse("08:00:00"));


//        BDDMockito
//                .when(service.saveOne(point))
//                .thenReturn(point);
        ResponseEntity<Point> response = restTemplate.exchange("/point/admin", HttpMethod.POST, null, Point.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    @WithMockUser(username = "rhumanos2", password = "senha123", roles = {"USER"})
    public void listPointShouldReturnStatusCode200() throws Exception {
        Employee e = new Employee();
        e.setName("Juarez Ramalho");
        e.setUsername("rhumanos2");
        e.setPassword(PasswordEncoder.encode("senha123"));
        e.setAdmin(false);
        e.setCpf("459.868.778-58");
        e.setIsBlocked(false);
        e.setIsActive(true);
        e.setBlockCauseMessage("");
        e.setType(Employee.TYPE.RH);

        this.restTemplate = restTemplate.withBasicAuth("rhumanos2", "senha123");
        this.restTemplate.getRestTemplate()
                .setInterceptors(Collections.singletonList(new RequestResponseLoggingInterceptor()));
        Employee employee = new Employee();
        Employee employee2 = new Employee();
        employee.setCode(5456L);
        employee2.setCode(5457L);
        List<Point> points = asList(
                Point.PointBuilder.aPoint().withEmployee(employee).build(),
                Point.PointBuilder.aPoint().withEmployee(employee2).build()
        );
        BDDMockito.when(service.listAll()).thenReturn(points);
        ResponseEntity<String> res = this.restTemplate
                .getForEntity("/point/protected/", String.class);
        System.out.println(res);
        assertThat(res.getStatusCodeValue()).isEqualTo(200);
    }
}
