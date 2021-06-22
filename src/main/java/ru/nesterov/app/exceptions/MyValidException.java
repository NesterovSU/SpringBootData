package ru.nesterov.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Sergey Nesterov
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Not valid") // 400
public class MyValidException extends RuntimeException {
}
