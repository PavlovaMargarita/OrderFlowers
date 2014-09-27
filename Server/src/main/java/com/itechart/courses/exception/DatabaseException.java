package com.itechart.courses.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by User on 26.09.14.
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "The database does not respond")
public class DatabaseException extends RuntimeException {}
