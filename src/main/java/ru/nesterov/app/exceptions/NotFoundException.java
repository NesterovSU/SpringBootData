package ru.nesterov.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Sergey Nesterov
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such ID") //404
public class NotFoundException extends RuntimeException{
}
