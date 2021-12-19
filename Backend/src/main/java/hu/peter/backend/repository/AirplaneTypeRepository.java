package hu.peter.backend.repository;

import hu.peter.backend.entity.AirplaneType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirplaneTypeRepository extends JpaRepository<AirplaneType,Long> {
    Optional<AirplaneType> findAirplaneTypeByTypeName(String typeName);
}
