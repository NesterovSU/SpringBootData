package ru.nesterov.app.domain;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Sergey Nesterov
 */
public class YearValidator implements ConstraintValidator<YearValid, String> {
    @Override
    public boolean isValid(String year, ConstraintValidatorContext constraintValidatorContext) {
        if (year == null || year.isEmpty() || !year.matches("\\d{4}")){return true;}
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        int nowYear = Integer.parseInt(dateFormat.format(date));
        int yearInt = Integer.parseInt(year);
        return yearInt  <= nowYear && yearInt >= 1900;
    }
}
