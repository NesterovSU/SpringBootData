package ru.nesterov.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.nesterov.app.domain.Car;
import ru.nesterov.app.repositories.CarRepo;
import javax.validation.Valid;


/**
 * @author Sergey Nesterov
 */
@Controller
public class MyController {
    @Autowired
    CarRepo carRepo;

    //Главная страница. Возвращаеются записи из базы по фильтру
    @GetMapping("/")
    public String getMainPage(
            @RequestParam(name = "brand", required = false, defaultValue = "") String brand,
            @RequestParam(name = "model", required = false, defaultValue = "") String model,
            @RequestParam(name = "color", required = false, defaultValue = "") String color,
            @RequestParam(name = "year", required = false, defaultValue = "") String year,
            Model m) {
        m.addAttribute("cars", carRepo.findByBrandContainingAndModelContainingAndColorContainingAndYearContaining(
                brand.toUpperCase(),
                model.toUpperCase(),
                color.toLowerCase(),
                year));
        m.addAttribute("car", new Car());
        m.addAttribute("statistic", carRepo.getStatistic());
        return "main";
    }


    //Удаление одной записи по ID
    @DeleteMapping("/")
    public String deleteCar(@RequestParam("id") Long id, Model m) {
        try {
            carRepo.deleteById(id);
            m.addAttribute("message", "Запись успешно удалена!");
        } catch (org.springframework.dao.EmptyResultDataAccessException exception) {
            m.addAttribute("error", "Записи не существует!");
        }
        m.addAttribute("cars", carRepo.findAll());
        m.addAttribute("car", new Car());
        m.addAttribute("statistic", carRepo.getStatistic());
        return "main";
    }

    //Добавление одной записи
    @PostMapping("/")
    public String addCar(
            @ModelAttribute("car") @Valid Car car, BindingResult bindingResult,
            Model m) {
        if (!bindingResult.hasErrors()) {
            try {
            carRepo.save(car);
            m.addAttribute("message", "Запись успешно добавелена!");
            m.addAttribute("car", new Car());
            }
            catch (DataIntegrityViolationException exception) {
            m.addAttribute("error", "Веденный регистрационный номер уже существует в базе данных!");
            }
        }
        m.addAttribute("cars", carRepo.findAll());
        m.addAttribute("statistic", carRepo.getStatistic());
        return "main";
    }
}