package hu.peter.backend.repository;

import hu.peter.backend.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AirportRepository extends JpaRepository<Airport,Long> {
   Optional<Airport> findAirportByCity(String city);
   List<Airport> findAirportsByCityStartsWith(String prefix);
}
