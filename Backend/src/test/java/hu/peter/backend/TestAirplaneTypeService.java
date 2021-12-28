package hu.peter.backend;

import hu.peter.backend.entity.AirplaneType;
import hu.peter.backend.repository.AirplaneTypeRepository;
import hu.peter.backend.service.AirplaneTypeService;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

@NoArgsConstructor
public class TestAirplaneTypeService {

    @Autowired
    private  AirplaneTypeService airplaneTypeService;
    @Autowired
    private  AirplaneTypeRepository repository;


    @BeforeAll
    public void init() {
        repository.deleteAll();
        airplaneTypeService.createAirplaneType("TestPlaneType",1,1);
        airplaneTypeService.createAirplaneType("TestPlaneType2",1,1);
    }

    @Test
    public void test() {
        List<AirplaneType> result = airplaneTypeService.getAllAirplaneType();
        assertEquals(2,result.size());
    }

    @AfterAll
    public void afterClear() {
        repository.deleteAll();
    }
}
