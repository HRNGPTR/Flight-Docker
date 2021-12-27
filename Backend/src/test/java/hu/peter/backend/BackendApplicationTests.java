package hu.peter.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@SpringBootTest
class BackendApplicationTests {

    @Test
    void contextLoads() {

    }

    @Test
    void testSum() {
        int a = 2;
        int b = 3;
        assertEquals(5,a+b);
    }

}
