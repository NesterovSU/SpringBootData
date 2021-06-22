package ru.nesterov.app.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.nesterov.app.domain.Car;

/**
 * @author Sergey Nesterov
 */
public interface CarRepo extends CrudRepository<Car, Long> {

    Iterable<Car> findByBrandContainingAndModelContainingAndColorContainingAndYearContaining(
            String brand, String model, String color, String year);


    @Query(value = "SELECT CONCAT(" +
            "'Всего записей: ', count(*)," +
            "', первая: ', MIN(timestamp)," +
            "', последняя: ', MAX(timestamp))" +
            "FROM public.car", nativeQuery = true)
    String getStatistic();
}
