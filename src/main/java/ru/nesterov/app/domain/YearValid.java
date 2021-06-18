package ru.nesterov.app.domain;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Sergey Nesterov
 */
@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = YearValidator.class)
@Documented
public @interface YearValid {
    String message() default "Год изготовления должен быть с 1900 по настоящий";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}