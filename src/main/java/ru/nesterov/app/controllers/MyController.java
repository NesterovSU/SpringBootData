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
import java.text.SimpleDateFormat;


/**
 * @author Sergey Nesterov
 */
@Controller
public class MyController {
    @Autowired
    CarRepo carRepo;

    //Главная страница. Возвращаеются все записи из базы
    @GetMapping("/")
    public String getMainPage(Model m){
        m.addAttribute("car", new Car());
        m.addAttribute("cars", carRepo.findAll());
        showStatistics(m);
        return "main";
    }

    //Страница подтверждения удаления записи
    @GetMapping("/{id}/delete")
    public String carDeletingPage(@PathVariable("id") Long id, Model m){
        Car car = carRepo.findById(id).stream().findAny().orElse(null);
        if (car == null) {
            m.addAttribute("error", "Записи не существует!");
            return getMainPage(m);
        }
        m.addAttribute("car", car);
        return "delete";
    }

    //Удаление записи
    @DeleteMapping("/")
    public String deleteCar(@RequestParam("id") Long id, Model m){
        try{
            carRepo.deleteById(id);
            m.addAttribute("message", "Запись успешно удалена!");
        }
        catch (org.springframework.dao.EmptyResultDataAccessException exception){
            m.addAttribute("error", "Записи не существует!");
        }
        catch (Exception exception){
            m.addAttribute("error", exception.getCause().getMessage());
        }
        return getMainPage(m);
    }

    //Добавление записи
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
        try {
            carRepo.save(car);
            m.addAttribute("message", "Запись успешно добавелена!");
        }
        catch (DataIntegrityViolationException exception){
            if (exception.getMostSpecificCause().getMessage().matches(".*\\n.*уже\\sсуществует.*?")){
                m.addAttribute("error", "Веденный регистрационный номер уже существует в базе данных!");
            }
            else {
                m.addAttribute("error", exception.getMostSpecificCause().getMessage());
            }
        }
        catch (Exception exception){
            m.addAttribute("error", exception.getCause().getMessage());
        }
        return getMainPage(m);
    }

    //Возвращаются записи по фильтру
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


    //Подготовка статистики базы
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
