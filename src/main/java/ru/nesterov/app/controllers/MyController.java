package ru.nesterov.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.nesterov.app.domain.Car;
import ru.nesterov.app.repositories.CarRepo;
import javax.validation.Valid;
import java.text.SimpleDateFormat;


/**
 * @author Sergey Nesterov
 */
@Controller
public class MyController {
    @Autowired
    CarRepo carRepo;

    @GetMapping("/")
    public String getMainPage(Model m){
        m.addAttribute("car", new Car());
        m.addAttribute("cars", carRepo.findAll());
        showStatistics(m);
        return "main";
    }

    @GetMapping("/{id}/delete")
    public String carDeletingPage(@PathVariable("id") Long id, Model m){
        m.addAttribute("car", carRepo.findById(id).stream().findAny().orElse(null));
        return "delete";
    }

    @DeleteMapping("/")
    public String deleteCar(@RequestParam("id") Long id, Model m){
        carRepo.deleteById(id);
        m.addAttribute("message", "Запись успешно удалена!");
        return getMainPage(m);
    }

    @PostMapping("/")
    public String addCar(
            @ModelAttribute("car") @Valid Car car, BindingResult bindingResult,
            Model m)
    {
        if (bindingResult.hasErrors()){
            m.addAttribute("cars", carRepo.findAll());
            showStatistics(m);
            return "main";
        }
        if (carRepo.findByNumber(car.getNumber()).iterator().hasNext()){
            m.addAttribute("error", "Веденный номер уже существует в базе данных");
            m.addAttribute("cars", carRepo.findAll());
            showStatistics(m);
            return "main";
        }

        carRepo.save(car);
        m.addAttribute("message", "Запись успешно добавелена!");
        return getMainPage(m);
    }

    @PostMapping("/filter")
    public String findCar(
            @RequestParam(name = "brand", required = false) String brand,
            @RequestParam(name = "model", required = false) String model,
            @RequestParam(name = "color", required = false) String color,
            @RequestParam(name = "year", required = false) String year,
            Model m)
    {
        m.addAttribute("cars", carRepo.findByBrandContainingAndModelContainingAndColorContainingAndYearContaining(
                brand.toUpperCase(),
                model.toUpperCase(),
                color.toLowerCase(),
                year));

        m.addAttribute("car", new Car());
        showStatistics(m);
        return "main";
    }



    public void showStatistics(Model m){
        String statistics;
        if(carRepo.findLastTimeStamp() != null && carRepo.findFirstTimeStamp() != null) {
            statistics = String.format("Статистика базы: всего записей - %d, время первой записи - %s," +
                            " время последней записи - %s",
                    carRepo.count(),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S")
                            .format(carRepo.findFirstTimeStamp()),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S")
                            .format(carRepo.findLastTimeStamp()));
        }else {
            statistics = String.format("Статистика базы: всего записей - %d, время первой записи - %s," +
                            " время последней записи - %s",
                    carRepo.count(), "", "");
        }
        m.addAttribute("statistics", statistics);
    }

}
