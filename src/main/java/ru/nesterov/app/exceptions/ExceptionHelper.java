package ru.nesterov.app.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * @author Sergey Nesterov
 */
@ControllerAdvice
public class ExceptionHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHelper.class);

    @ResponseStatus(value=HttpStatus.CONFLICT,
            reason="Data integrity violation") //409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void conflict() {}

    @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such record") //404
    @ExceptionHandler(org.springframework.dao.EmptyResultDataAccessException.class)
    public void notFound(){}
}

