package ru.nesterov.app.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.nesterov.app.domain.Car;

import java.sql.Timestamp;

/**
 * @author Sergey Nesterov
 */
public interface CarRepo extends CrudRepository<Car, Long> {

    Iterable<Car> findByBrandContainingAndModelContainingAndColorContainingAndYearContaining(
            String brand, String model, String color, String year);

    Iterable<Car> findByNumber(String number);

    @Query(value = "SELECT timestamp FROM car ORDER BY timestamp DESC LIMIT 1", nativeQuery = true)
    Timestamp findLastTimeStamp();

    @Query(value = "SELECT timestamp FROM car ORDER BY timestamp ASC LIMIT 1", nativeQuery = true)
    Timestamp findFirstTimeStamp();
}
