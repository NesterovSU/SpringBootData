package ru.nesterov.app.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * @author Sergey Nesterov
 */
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    @NotEmpty(message = "Необходимо ввести марку!")
    @Size(max=20, message = "Поле марка вмещает до 20 символов")
    private String brand;

    @NotEmpty(message = "Необходимо ввести модель!")
    @Size(max=20, message = "Поле модель вмещает до 20 символов")
    private String model;

    @NotEmpty(message = "Необходимо ввести цвет!")
    @Size(max=20, message = "Поле цвет вмещает до 20 символов")
    private String color;

    @NotBlank(message = "Необходимо ввести год выпуска!")
    @Pattern(regexp = "(\\d{4})",
            message = "Год должен быть четырехзначным (например: 1955)")
    @YearValid
    private String year;

    @Column(unique = true)
    @Pattern(regexp = "[авекмнорстухАВЕКМНОРСТУХabekmhopctyxABEKMHOPCTYX]{1}\\d{3}" +
            "[авекмнорстухАВЕКМНОРСТУХabekmhopctyxABEKMHOPCTYX]{2}\\d{2,3}",
                message = "Номер должен соответствовать шаблону (например: А123АА37)")
    private String number;



    public Car() {
    }


    @Override
    public String toString(){
        return String.format("%s  %s  (%s)  произведён в %s  [%s]      ", brand, model, color,year, number);
    }

    public Long getId() {
        return id;
    }

    public Timestamp getTimeStamp() { return timestamp;}

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand.toUpperCase();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model.toUpperCase();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color.toLowerCase();
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
