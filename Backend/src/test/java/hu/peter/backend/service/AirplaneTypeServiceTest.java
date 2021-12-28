package hu.peter.backend.service;

import hu.peter.backend.entity.AirplaneType;
import hu.peter.backend.repository.AirplaneTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AirplaneTypeServiceTest {

    @Mock
    AirplaneTypeRepository airplaneTypeRepository;

    @InjectMocks
    AirplaneTypeService airplaneTypeService;

    @BeforeEach
    public void init() {
        when(airplaneTypeRepository.save(any(AirplaneType.class)))
                .then(returnsFirstArg());
    }

    @Test
    public void testGetAllAirplaneType() {
        AirplaneType airplaneType737 = airplaneTypeService.createAirplaneType("Boeing737", 1, 1);
        AirplaneType airplaneType747 = airplaneTypeService.createAirplaneType("Boeing747", 1, 1);
        given(airplaneTypeRepository.findAll()).willReturn(asList(airplaneType737, airplaneType747));
        List<AirplaneType> airplaneTypes = airplaneTypeService.getAllAirplaneType();
        assertThat(airplaneTypes).containsExactly(airplaneType737, airplaneType747);
    }

    @Test
    public void testGetAirplaneTypeByNameIfGivenTypeExist(){
        AirplaneType airplaneType737 = airplaneTypeService.createAirplaneType("Boeing737", 1, 1);
        when(airplaneTypeRepository.findAirplaneTypeByTypeName("Boeing737")).thenReturn(Optional.of(airplaneType737));
        given(airplaneTypeRepository.findAirplaneTypeByTypeName("Boeing737")).willReturn(Optional.of(airplaneType737));
        Optional<AirplaneType> airplaneType = airplaneTypeService.getAirplaneTypeByName("Boeing737");
        assertThat(airplaneType.get()).isSameAs(airplaneType737);
    }

    @Test
    public void testGetAirplaneTypeByNameIfGivenTypeDoesNotExist() {
        AirplaneType airplaneType737 = airplaneTypeService.createAirplaneType("Boeing737", 1, 1);
        given(airplaneTypeRepository.findAirplaneTypeByTypeName(not(eq("Boeing737")))).willReturn(Optional.empty());
        Optional<AirplaneType> airplaneType = airplaneTypeService.getAirplaneTypeByName("Boeing747");
        assertThat(airplaneType).isNotPresent();
    }
}