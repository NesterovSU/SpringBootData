package ru.nesterov.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.nesterov.app.domain.Car;
import ru.nesterov.app.exceptions.MyValidException;
import ru.nesterov.app.repositories.CarRepo;
import javax.validation.Valid;

/**
 * @author Sergey Nesterov
 */
@RestController
@RequestMapping("rest")
public class MyRestController {
    @Autowired
    CarRepo carRepo;

    @GetMapping
    public Iterable<Car> getAll(@RequestParam(name = "brand", required = false, defaultValue = "") String brand,
                                @RequestParam(name = "model", required = false, defaultValue = "") String model,
                                @RequestParam(name = "color", required = false, defaultValue = "") String color,
                                @RequestParam(name = "year", required = false, defaultValue = "") String year)
    {
        return carRepo.findByBrandContainingAndModelContainingAndColorContainingAndYearContaining(
                brand.toUpperCase(),
                model.toUpperCase(),
                color.toLowerCase(),
                year);
    }


    @PostMapping
    public void addCar(@RequestBody @Valid Car car, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new MyValidException();
        }
        carRepo.save(car);
    }

    @DeleteMapping
    public void deleteCar(@RequestParam(name = "id") Long id){
        carRepo.deleteById(id);
    }

    @GetMapping("stat")
    public String getDbStatistic(){
        return carRepo.getStatistic();
    }

}
